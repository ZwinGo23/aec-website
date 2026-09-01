package api.aec.controllers.models;

public record ApiError (
        String code,
        String message
){
}
