package api.aec.controllers.models;

import java.time.LocalDate;

public record AnnouncementRequest(
        String summary,
        String description,
        LocalDate validityDate
) {
}
