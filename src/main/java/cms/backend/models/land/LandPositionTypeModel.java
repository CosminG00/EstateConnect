package cms.backend.models.land;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(exclude = "lands")
@NoArgsConstructor
@Table(name="land_position_types")
public class LandPositionTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "positionType", cascade = CascadeType.ALL)
    private Set<LandModel> lands = new HashSet<>();
}
