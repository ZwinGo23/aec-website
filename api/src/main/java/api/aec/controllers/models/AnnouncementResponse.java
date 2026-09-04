package api.aec.controllers.models;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record AnnouncementResponse (
        Long id,
        String summary,
        String description,
        LocalDate validityDate,
        LocalDateTime updateDate,
        UserResponse updateUser
){
}
