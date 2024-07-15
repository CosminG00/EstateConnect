package cms.backend.models.land;

import cms.backend.models.advert.AdvertModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(exclude = "advert")
@NoArgsConstructor
@Table(name="lands")
public class LandModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @MapsId
    @JoinColumn(name="advert_id")
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private AdvertModel advert;

    @Column(name="street_front", nullable = false)
    private float streetFront;

    @ManyToOne
    @JoinColumn(name="land_type_id", nullable = false)
    private LandTypeModel type;

    @ManyToOne
    @JoinColumn(name="land_position_type_id", nullable = false)
    private LandPositionTypeModel positionType;
}
