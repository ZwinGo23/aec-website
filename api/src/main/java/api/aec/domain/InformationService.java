package api.aec.domain;

import api.aec.domain.exceptions.BadRequestException;
import api.aec.domain.exceptions.NotFoundException;
import api.aec.domain.mappers.InformationMapper;
import api.aec.domain.models.PresentationModel;
import api.aec.repositories.PresentationRepository;
import api.aec.repositories.entities.PresentationEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InformationService {

    private final PresentationRepository presentationRepository;
    private final InformationMapper informationMapper;

    public InformationService(PresentationRepository presentationRepository, InformationMapper informationMapper) {
        this.presentationRepository = presentationRepository;
        this.informationMapper = informationMapper;
    }

    public PresentationModel getPresentation() {
        PresentationEntity presentation = presentationRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException("Aucune présentation trouvée."));

        return informationMapper.mapToPresentationModel(presentation);
    }

    @Transactional
    public void updatePresentation(PresentationModel model) {
        if(model == null || model.content() == null || model.content().isBlank()) {
            throw new BadRequestException("La présentation ne peut pas être vide.");
        }

        PresentationEntity presentation = presentationRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException("Aucune présentation trouvée."));

        presentation.setContent(model.content());
    }
}
