package api.aec.domain.models;

import java.time.LocalDate;

public record RegisterUserModel(
        String email,
        String password,
        String phoneNumber,
        String firstName,
        String lastName,
        GenderModel gender,
        LocalDate birthDate
) {
}
