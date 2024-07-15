package cms.backend.repositories.advert;

import cms.backend.models.advert.AdvertModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertRepository extends JpaRepository<AdvertModel, UUID> {
    Optional<AdvertModel> findByTitle(String title);
    List<AdvertModel> findByAgency_Id(UUID agencyId);//adaugat nou
}