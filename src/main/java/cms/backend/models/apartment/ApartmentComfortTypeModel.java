package cms.backend.models.apartment;

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
@ToString(exclude = "apartments")
@NoArgsConstructor
@Table(name="apartment_comfort_types")
public class ApartmentComfortTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "comfortType", cascade = CascadeType.ALL)
    private Set<ApartmentModel> apartments = new HashSet<>();
}
