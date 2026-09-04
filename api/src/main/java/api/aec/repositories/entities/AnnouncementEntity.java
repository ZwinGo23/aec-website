package api.aec.repositories.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "announcements", schema = "aec")
public class AnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "summary", nullable = false)
    private String summary;

    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    @Setter
    @Column(name = "validity_date", nullable = false)
    private LocalDate validityDate;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @Setter
    @ManyToOne
    @JoinColumn(name = "update_user", nullable = false)
    private UserEntity updateUser;

    @Builder
    public AnnouncementEntity(String summary, String description, LocalDate validityDate) {
        this.summary = summary;
        this.description = description;
        this.validityDate = validityDate;
    }

    @PrePersist
    private void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
