package cms.backend.repositories.apartament;

import cms.backend.models.apartment.ApartmentComfortTypeModel;
import cms.backend.models.house.HouseTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApartmentComfortTypeRepository extends JpaRepository<ApartmentComfortTypeModel, UUID> {
    Optional<ApartmentComfortTypeModel> findByName(String name);
}
