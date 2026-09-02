package api.aec.domain.exceptions;

public class PresentationNotFoundException extends RuntimeException {

    public PresentationNotFoundException() {
        super("Aucune présentation trouvée.");
    }
}
