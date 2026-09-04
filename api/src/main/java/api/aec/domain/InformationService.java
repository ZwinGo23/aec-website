package api.aec.domain;

import api.aec.exceptions.BadRequestException;
import api.aec.exceptions.NotFoundException;
import api.aec.domain.mappers.InformationMapper;
import api.aec.domain.models.AnnouncementModel;
import api.aec.domain.models.PresentationModel;
import api.aec.repositories.AnnouncementRepository;
import api.aec.repositories.PresentationRepository;
import api.aec.repositories.UserRepository;
import api.aec.repositories.entities.AnnouncementEntity;
import api.aec.repositories.entities.PresentationEntity;
import api.aec.repositories.entities.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
public class InformationService {

    private final PresentationRepository presentationRepository;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final InformationMapper informationMapper;

    public InformationService(PresentationRepository presentationRepository, AnnouncementRepository announcementRepository, UserRepository userRepository, InformationMapper informationMapper) {
        this.presentationRepository = presentationRepository;
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
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

    public Set<AnnouncementModel> getValidAnnouncements() {
        LocalDate currentDate = LocalDate.now();
        Set<AnnouncementEntity> entities = announcementRepository.findAllByValidityDateGreaterThanEqual(currentDate);
        return informationMapper.mapToAnnouncementModelSet(entities);
    }

    @Transactional
    public void createAnnouncement(AnnouncementModel model, long userId) {
        if(model == null || model.summary().isBlank() || model.description().isBlank() || model.validityDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("L'annonce n'est pas valide.");
        }
        AnnouncementEntity entity = informationMapper.mapToAnnouncementEntity(model);
        UserEntity user = userRepository.getReferenceById(userId);
        entity.setUpdateUser(user);
        announcementRepository.save(entity);
    }

    @Transactional
    public void updateAnnouncement(AnnouncementModel model, long announceId, long userId) {
        if(model == null || model.summary().isBlank() || model.description().isBlank() || model.validityDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("L'annonce n'est pas valide.");
        }
        AnnouncementEntity entity = announcementRepository.findById(announceId)
                .orElseThrow(() -> new NotFoundException("L'annonce n'existe pas."));
        UserEntity user = userRepository.getReferenceById(userId);
        entity.setSummary(model.summary());
        entity.setDescription(model.description());
        entity.setValidityDate(model.validityDate());
        entity.setUpdateUser(user);
    }

    public AnnouncementModel getAnnouncement(long id) {
        AnnouncementEntity entity = announcementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cette annonce n'existe pas."));

        return informationMapper.mapToAnnouncementModel(entity);
    }
}
