package api.aec.domain.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AnnouncementModel(
        Long id,
        String summary,
        String description,
        LocalDate validityDate,
        LocalDateTime updateDate,
        UserModel updateUser
) {
}
