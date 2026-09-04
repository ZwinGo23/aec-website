package api.aec.controllers.models;

import api.aec.domain.models.RoleModel;

public record AuthenticatedUser(
        Long id,
        String email,
        RoleModel role
) {
}
