package cms.backend.repositories.advert;

import cms.backend.models.advert.AdvertPhotoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdvertPhotoRepository extends JpaRepository<AdvertPhotoModel, UUID> {
}