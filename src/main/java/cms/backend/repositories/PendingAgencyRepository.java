package cms.backend.repositories;

import cms.backend.models.PendingAgencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingAgencyRepository extends JpaRepository<PendingAgencyModel, UUID> {
    Optional<PendingAgencyModel> findByPendingUserEmail(String email);
    Optional<PendingAgencyModel> findByPendingUserToken(String token);
}
