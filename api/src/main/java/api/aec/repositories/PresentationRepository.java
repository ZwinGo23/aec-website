package api.aec.repositories;

import api.aec.repositories.entities.PresentationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationRepository extends JpaRepository<PresentationEntity, Long> {
}
