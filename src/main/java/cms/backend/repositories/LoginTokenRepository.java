package cms.backend.repositories;

import cms.backend.models.LoginTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginTokenRepository extends JpaRepository<LoginTokenModel, UUID> {
}
