package api.aec.domain.mappers;

import api.aec.controllers.models.RegisterUserRequest;
import api.aec.domain.models.RegisterUserModel;
import api.aec.repositories.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    RegisterUserModel mapToRegisterUserModel(RegisterUserRequest request);

    @Mapping(source = "email", target = "email")
    UserEntity mapToUserEntity(RegisterUserModel model, String email, String passwordHash);
}
