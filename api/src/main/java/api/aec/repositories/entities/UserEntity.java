package api.aec.repositories.entities;

import api.aec.domain.models.GenderModel;
import api.aec.domain.models.RoleModel;
import api.aec.repositories.entities.converters.GenderConverter;
import api.aec.repositories.entities.converters.RoleConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", schema = "aec")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Convert(converter = GenderConverter.class)
    @Column(name = "gender", nullable = false)
    private GenderModel gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "member", nullable = false)
    private Boolean isMember;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role", nullable = false)
    private RoleModel role;

    @Column(name = "enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Builder
    public UserEntity(String email, String passwordHash, String phoneNumber, String firstName, String lastName, GenderModel gender, LocalDate birthDate) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    @PrePersist
    private void onCreate() {
        this.isMember = false;
        this.role = RoleModel.USER;
        this.isEnabled = true;
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
