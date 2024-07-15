package cms.backend.models.advert;

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
@ToString(exclude = {"adverts"})
@NoArgsConstructor
@Table(name="advert_types")
public class AdvertTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "advertType", cascade = CascadeType.ALL)
    private Set<AdvertModel> adverts = new HashSet<>();
}
