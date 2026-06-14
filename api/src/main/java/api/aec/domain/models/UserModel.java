package api.aec.domain.models;

import java.time.LocalDate;

public record UserModel(
        String email,
        String phoneNumber,
        String firstName,
        String lastName,
        GenderModel gender,
        LocalDate birthDate
) {
}
