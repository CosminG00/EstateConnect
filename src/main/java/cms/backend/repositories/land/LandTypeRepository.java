package cms.backend.repositories.land;

import cms.backend.models.house.HouseTypeModel;
import cms.backend.models.land.LandTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LandTypeRepository extends JpaRepository<LandTypeModel, UUID> {
    Optional<LandTypeModel> findByName(String name);
}
