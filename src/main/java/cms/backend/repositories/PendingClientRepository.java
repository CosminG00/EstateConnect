package cms.backend.repositories;

import cms.backend.models.PendingClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingClientRepository extends JpaRepository<PendingClientModel, UUID> {
    Optional<PendingClientModel> findByPendingUserEmail(String email);
    Optional<PendingClientModel> findByPendingUserToken(String token);
}
