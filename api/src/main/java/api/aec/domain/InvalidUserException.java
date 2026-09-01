package api.aec.domain;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("Tous les champs sont obligatoires.");
    }
}
