package api.aec.domain.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Cette adresse email est déjà utilisée.");
    }
}
