package cms.backend.repositories.apartament;

import cms.backend.models.apartment.ApartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentModel, UUID> {
}
