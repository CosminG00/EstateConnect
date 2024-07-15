package cms.backend.repositories;

import cms.backend.models.ChangePasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChangePasswordRepository extends JpaRepository<ChangePasswordModel, UUID> {
    Optional<ChangePasswordModel> findByToken(String token);
}