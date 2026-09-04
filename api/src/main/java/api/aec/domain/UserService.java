package api.aec.domain;

import api.aec.exceptions.ConflictException;
import api.aec.exceptions.BadRequestException;
import api.aec.domain.mappers.UserMapper;
import api.aec.domain.models.RegisterUserModel;
import api.aec.repositories.UserRepository;
import api.aec.repositories.entities.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public void register(RegisterUserModel model) {
        verifyRegisterUserModelIsValid(model);

        String email = model.email()
                .trim()
                .toLowerCase();
        verifyEmailIsUnique(email);

        String passwordHash = passwordEncoder.encode(model.password());

        UserEntity user = userMapper.mapToUserEntity(model, email, passwordHash);
        userRepository.save(user);
    }

    private void verifyRegisterUserModelIsValid(RegisterUserModel model) {
        if(model.email().isBlank()
                || model.password().isBlank()
                || model.phoneNumber().isBlank()
                || model.firstName().isBlank()
                || model.lastName().isBlank()
                ||model.gender() == null
                || model.birthDate() == null) {
            throw new BadRequestException("Tous les champs sont obligatoires.");
        }
    }

    private void verifyEmailIsUnique(String email) {
        if(userRepository.existsByEmailIgnoreCase(email))
            throw new ConflictException("Cette adresse email est déjà utilisée.");
    }
}
