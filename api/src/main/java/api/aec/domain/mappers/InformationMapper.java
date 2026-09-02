package api.aec.domain.mappers;

import api.aec.controllers.models.PresentationRequest;
import api.aec.controllers.models.PresentationResponse;
import api.aec.domain.models.PresentationModel;
import api.aec.repositories.entities.PresentationEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface InformationMapper {

    PresentationModel mapToPresentationModel(PresentationEntity presentationEntity);

    PresentationResponse mapToPresentationResponse(PresentationModel presentationModel);

    PresentationModel mapToPresentationModel(PresentationRequest presentationRequest);
}
