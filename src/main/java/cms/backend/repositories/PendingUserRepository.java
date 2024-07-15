package cms.backend.repositories;

import cms.backend.models.PendingUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingUserRepository extends JpaRepository<PendingUserModel, UUID> {
    Optional<PendingUserModel> findByEmail(String email);
    Optional<PendingUserModel> findByToken(String token);
}
