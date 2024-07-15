package cms.backend.repositories;

import cms.backend.models.ChangeEmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChangeEmailRepository extends JpaRepository<ChangeEmailModel, UUID> {
    Optional<ChangeEmailModel> findByToken(String token);
}