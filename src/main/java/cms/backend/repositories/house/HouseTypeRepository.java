package cms.backend.repositories.house;

import cms.backend.models.house.HouseTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HouseTypeRepository extends JpaRepository<HouseTypeModel, UUID> {
    Optional<HouseTypeModel> findByName(String name);
}
