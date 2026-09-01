package api.aec.controllers.models;

import java.time.LocalDate;

public record RegisterUserRequest (
        String email,
        String password,
        String phoneNumber,
        String firstName,
        String lastName,
        String gender,
        LocalDate birthDate
){
}
