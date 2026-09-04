package api.aec.controllers;

import api.aec.controllers.models.RegisterUserRequest;
import api.aec.domain.UserService;
import api.aec.domain.mappers.UserMapper;
import api.aec.domain.models.RegisterUserModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-aec/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @PreAuthorize("isAnonymous()")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterUserRequest request) {
        RegisterUserModel model = userMapper.mapToRegisterUserModel(request);
        userService.register(model);
    }
}
