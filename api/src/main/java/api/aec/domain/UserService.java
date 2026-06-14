package api.aec.domain;

import api.aec.domain.mappers.UserMapper;
import api.aec.domain.models.UserModel;
import api.aec.repositories.UserRepository;
import api.aec.repositories.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserModel registerMember(final UserModel userModel) {
        final UserEntity userEntity = userMapper.mapToUserEntity(userModel);
        final UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.mapToUserModel(savedUser);
    }
}
