package api.aec.controllers.handlers;

import api.aec.controllers.models.ApiError;
import api.aec.domain.InvalidUserException;
import api.aec.domain.exceptions.EmailAlreadyUsedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyUsed(EmailAlreadyUsedException exception) {
        ApiError error = new ApiError("EMAIL_ALREADY_USED", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ApiError> handleInvalidUser(InvalidUserException exception) {
        ApiError error = new ApiError("INVALID_USER", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
