package api.aec.domain.mappers;

import api.aec.domain.models.UserModel;
import api.aec.repositories.entities.UserEntity;
import org.mapstruct.Mapper;

import static api.aec.domain.constants.Constants.COMPONENT_MODEL;


@Mapper(componentModel = COMPONENT_MODEL)
public interface UserMapper {

    UserEntity mapToUserEntity(UserModel userModel);

    UserModel mapToUserModel(UserEntity userEntity);
}
