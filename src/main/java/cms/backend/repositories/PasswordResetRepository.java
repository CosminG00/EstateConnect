package cms.backend.repositories;

import cms.backend.models.PasswordResetModel;
import cms.backend.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordResetModel, UUID> {
    Optional<PasswordResetModel> findByToken(String token);
    Optional<PasswordResetModel> findByUserEmail(String email);
    void deleteByUser(UserModel userModel);
    void deleteByUserEmail(String email);
}
