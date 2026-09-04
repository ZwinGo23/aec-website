package api.aec.controllers.models;

public record AuthenticatedUserResponse(
        Long id,
        String email,
        String role
) {
}
