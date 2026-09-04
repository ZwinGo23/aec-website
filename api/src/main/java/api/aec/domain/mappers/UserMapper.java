package api.aec.domain.mappers;

import api.aec.controllers.models.RegisterUserRequest;
import api.aec.controllers.models.UserResponse;
import api.aec.domain.models.GenderModel;
import api.aec.domain.models.RegisterUserModel;
import api.aec.domain.models.RoleModel;
import api.aec.domain.models.UserModel;
import api.aec.repositories.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    RegisterUserModel mapToRegisterUserModel(RegisterUserRequest request);

    @Mapping(source = "email", target = "email")
    UserEntity mapToUserEntity(RegisterUserModel model, String email, String passwordHash);

    UserModel mapToUserModel(UserEntity entity);

    UserResponse mapToUserResponse(UserModel model);

    default String mapGenderToString(GenderModel gender) {
        if(gender == null) {
            throw new IllegalArgumentException("Le genre ne peut pas être null.");
        }
        return gender.name();
    }

    default String mapRoleToString(RoleModel role) {
        if(role == null) {
            throw new IllegalArgumentException("Le rôle ne peut pas être null");
        }
        return role.name();
    }
}
