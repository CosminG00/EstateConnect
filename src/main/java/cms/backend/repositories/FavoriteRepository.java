package cms.backend.repositories;

import cms.backend.models.FavoriteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<FavoriteModel, UUID> {
    void deleteByAdvertId(UUID advertId);
    List<FavoriteModel> findByClientId(UUID clientId);
}