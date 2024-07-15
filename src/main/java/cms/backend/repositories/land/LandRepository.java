package cms.backend.repositories.land;

import cms.backend.models.land.LandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LandRepository extends JpaRepository<LandModel, UUID> {

}
