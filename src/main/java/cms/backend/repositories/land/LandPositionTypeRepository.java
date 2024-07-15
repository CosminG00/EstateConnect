package cms.backend.repositories.land;

import cms.backend.models.house.HouseTypeModel;
import cms.backend.models.land.LandModel;
import cms.backend.models.land.LandPositionTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LandPositionTypeRepository extends JpaRepository<LandPositionTypeModel, UUID> {
    Optional<LandPositionTypeModel> findByName(String name);
}
