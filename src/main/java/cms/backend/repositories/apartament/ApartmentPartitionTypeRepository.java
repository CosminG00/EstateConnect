package cms.backend.repositories.apartament;

import cms.backend.models.apartment.ApartmentPartitionTypeModel;
import cms.backend.models.house.HouseTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApartmentPartitionTypeRepository extends JpaRepository<ApartmentPartitionTypeModel, UUID> {
    Optional<ApartmentPartitionTypeModel> findByName(String name);
}
