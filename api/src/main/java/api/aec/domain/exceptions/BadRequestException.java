package api.aec.domain.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String s) {
        super(s);
    }
}
