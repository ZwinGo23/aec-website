package api.aec.controllers;

import api.aec.controllers.models.PresentationRequest;
import api.aec.controllers.models.PresentationResponse;
import api.aec.domain.InformationService;
import api.aec.domain.exceptions.BadRequestException;
import api.aec.domain.mappers.InformationMapper;
import api.aec.domain.models.PresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/presentation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePresentation(@RequestBody PresentationRequest request) {
        if(request.content().isBlank()) {
            throw new BadRequestException("Une présentation ne peut pas être vide.");
        }
        PresentationModel model = informationMapper.mapToPresentationModel(request);
        informationService.updatePresentation(model);
    }
}
