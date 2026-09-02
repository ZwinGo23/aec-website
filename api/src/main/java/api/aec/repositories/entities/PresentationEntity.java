package api.aec.repositories.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "presentation", schema = "aec")
public class PresentationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Builder
    public PresentationEntity(String content) {
        this.content = content;
    }

    @PrePersist
    private void onCreate() {
        this.creationDate = LocalDate.now();
        this.updateDate = LocalDate.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updateDate = LocalDate.now();
    }
}
