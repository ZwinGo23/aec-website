package api.aec.auth;

import api.aec.controllers.models.AuthenticatedUser;
import api.aec.exceptions.UnauthorizedException;
import api.aec.repositories.UserRepository;
import api.aec.repositories.entities.UserEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationHandler implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationHandler(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        String email = authentication.getName();
        Object credentials = authentication.getCredentials();

        if (!(credentials instanceof String password)) {
            throw new UnauthorizedException("Identifiants incorrects.");
        }

        UserEntity user = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UnauthorizedException("Mauvais identifiants"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new UnauthorizedException("Identifiants incorrects");
        }

        if (!user.getIsEnabled()) {
            throw new UnauthorizedException("Compte désactivé");
        }

        AuthenticatedUser authenticatedUser =
                new AuthenticatedUser(
                        user.getId(),
                        user.getEmail(),
                        user.getRole()
                );

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().name().toUpperCase()
                )
        );

        return UsernamePasswordAuthenticationToken.authenticated(
                authenticatedUser,
                null,
                authorities
        );
    }

    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}
