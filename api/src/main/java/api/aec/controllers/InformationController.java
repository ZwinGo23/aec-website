package api.aec.controllers;

import api.aec.controllers.models.AnnouncementRequest;
import api.aec.controllers.models.AnnouncementResponse;
import api.aec.controllers.models.PresentationRequest;
import api.aec.controllers.models.PresentationResponse;
import api.aec.domain.InformationService;
import api.aec.exceptions.BadRequestException;
import api.aec.domain.mappers.InformationMapper;
import api.aec.domain.models.AnnouncementModel;
import api.aec.controllers.models.AuthenticatedUser;
import api.aec.domain.models.PresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api-aec/informations")
public class InformationController {

    private final InformationService informationService;
    private final InformationMapper informationMapper;

    public InformationController(InformationService informationService, InformationMapper informationMapper) {
        this.informationService = informationService;
        this.informationMapper = informationMapper;
    }

    @GetMapping("/presentation")
    public PresentationResponse getPresentation() {
        PresentationModel model = informationService.getPresentation();
        return informationMapper.mapToPresentationResponse(model);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/presentation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePresentation(@RequestBody PresentationRequest request) {
        if(request.content().isBlank()) {
            throw new BadRequestException("Une présentation ne peut pas être vide.");
        }
        PresentationModel model = informationMapper.mapToPresentationModel(request);
        informationService.updatePresentation(model);
    }

    @GetMapping("/announcements")
    public Set<AnnouncementResponse> getValidAnnouncements() {
        Set<AnnouncementModel> models = informationService.getValidAnnouncements();
        return informationMapper.mapToAnnouncementResponseSet(models);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/announcements")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnnouncement(@RequestBody AnnouncementRequest request, @AuthenticationPrincipal AuthenticatedUser user) {
        if(request == null) {
            throw new BadRequestException("Une annonce ne peut pas être null");
        }
        AnnouncementModel model = informationMapper.mapToAnnouncementModel(request);
        informationService.createAnnouncement(model, user.id());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/announcements/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnnouncement(@PathVariable long id, @RequestBody AnnouncementRequest request, @AuthenticationPrincipal AuthenticatedUser user) {
        if(request == null) {
            throw new BadRequestException("Une annonce ne peut pas être null");
        }
        AnnouncementModel model = informationMapper.mapToAnnouncementModel(request);
        informationService.updateAnnouncement(model, id, user.id());
    }

    @GetMapping("/announcements/{id}")
    public AnnouncementResponse getAnnouncement(@PathVariable long id) {
        AnnouncementModel model = informationService.getAnnouncement(id);
        return informationMapper.mapToAnnouncementResponse(model);
    }
}
