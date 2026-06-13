package api.aec.domain;

import api.aec.repositories.UserRepository;
import api.aec.repositories.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerMember() {
        UserEntity userEntity = new UserEntity("test", "test", "test", "test");
        userRepository.save(userEntity);
    }
}
