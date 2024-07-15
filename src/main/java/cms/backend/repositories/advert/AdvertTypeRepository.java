package cms.backend.repositories.advert;

import cms.backend.models.advert.AdvertTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertTypeRepository extends JpaRepository<AdvertTypeModel, UUID> {
    Optional<AdvertTypeModel> findByName(String name);
}