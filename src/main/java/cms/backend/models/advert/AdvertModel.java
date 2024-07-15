package cms.backend.models.advert;

import cms.backend.models.AgencyModel;
import cms.backend.models.apartment.ApartmentModel;
import cms.backend.models.house.HouseModel;
import cms.backend.models.land.LandModel;
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
@ToString(exclude = {"advertPhotos", "agency"})
@NoArgsConstructor
@Table(name="adverts")
public class AdvertModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float lat;

    @Column(nullable = false)
    private Float lng;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private float price;

    @Column(name="total_square_meters", nullable = false)
    private float totalSquareMeters;

    @ManyToOne
    @JoinColumn(name="advert_type_id", nullable = false)
    private AdvertTypeModel advertType;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AdvertPhotoModel> advertPhotos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="agency_id", nullable = false)
    private AgencyModel agency;

    @OneToOne(mappedBy = "advert", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private LandModel land;

    @OneToOne(mappedBy = "advert", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private HouseModel house;

    @OneToOne(mappedBy = "advert", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ApartmentModel apartment;
}
