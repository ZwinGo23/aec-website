package api.aec.domain.mappers;

import api.aec.controllers.models.AnnouncementRequest;
import api.aec.controllers.models.AnnouncementResponse;
import api.aec.controllers.models.PresentationRequest;
import api.aec.controllers.models.PresentationResponse;
import api.aec.domain.models.AnnouncementModel;
import api.aec.domain.models.PresentationModel;
import api.aec.repositories.entities.AnnouncementEntity;
import api.aec.repositories.entities.PresentationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface InformationMapper {

    PresentationModel mapToPresentationModel(PresentationEntity presentationEntity);

    PresentationResponse mapToPresentationResponse(PresentationModel presentationModel);

    PresentationModel mapToPresentationModel(PresentationRequest presentationRequest);

    Set<AnnouncementResponse> mapToAnnouncementResponseSet(Set<AnnouncementModel> models);

    AnnouncementModel mapToAnnouncementModel(AnnouncementEntity entity);

    Set<AnnouncementModel> mapToAnnouncementModelSet(Set<AnnouncementEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    AnnouncementModel mapToAnnouncementModel(AnnouncementRequest request);

    AnnouncementEntity mapToAnnouncementEntity(AnnouncementModel model);

    AnnouncementResponse mapToAnnouncementResponse(AnnouncementModel model);
}
