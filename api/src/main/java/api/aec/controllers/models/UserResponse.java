package api.aec.controllers.models;


import java.time.LocalDate;

public record UserResponse (
        String email,
        String phoneNumber,
        String firstName,
        String lastName,
        String gender,
        LocalDate birthDate,
        String role
){
}
