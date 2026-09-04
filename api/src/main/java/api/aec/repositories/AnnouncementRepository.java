package api.aec.repositories;

import api.aec.repositories.entities.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {

    public Set<AnnouncementEntity> findAllByValidityDateGreaterThanEqual(LocalDate date);
}
