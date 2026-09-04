package api.aec.controllers;

import api.aec.controllers.models.AuthenticatedUserResponse;
import api.aec.controllers.models.LoginRequest;
import api.aec.controllers.models.AuthenticatedUser;
import api.aec.exceptions.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-aec/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login(@RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        if (request == null || request.email() == null || request.email().isBlank() || request.password() == null || request.password().isBlank()) {
            throw new BadRequestException("L'email et le mot de passe sont obligatoires.");
        }
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                        request.email().trim(),
                        request.password());
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public AuthenticatedUserResponse getCurrentUser(@AuthenticationPrincipal AuthenticatedUser user) {
        return new AuthenticatedUserResponse(user.id(), user.email(), user.role().name());
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
    }
}