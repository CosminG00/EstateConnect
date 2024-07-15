package cms.backend.repositories;

import cms.backend.models.AgencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyModel, UUID> {
    Optional<AgencyModel> findByUserName(String username);
    Optional<AgencyModel> findByUserEmail(String email);
    Optional<AgencyModel> findByUserUsername(String username);
}