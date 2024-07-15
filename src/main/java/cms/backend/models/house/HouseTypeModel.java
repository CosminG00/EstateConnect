package cms.backend.models.house;

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
@ToString(exclude = "houses")
@NoArgsConstructor
@Table(name="house_types")
public class HouseTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "houseType", cascade = CascadeType.ALL)
    private Set<HouseModel> houses = new HashSet<>();
}
