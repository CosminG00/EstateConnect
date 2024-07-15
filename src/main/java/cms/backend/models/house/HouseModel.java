package cms.backend.models.house;

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
@Table(name="houses")
public class HouseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @MapsId
    @JoinColumn(name="advert_id")
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private AdvertModel advert;

    @Column(name="number_of_floors", nullable = false)
    private int numberOfFloors;

    @Column(name="number_of_rooms", nullable = false)
    private int numberOfRooms;

    @Column(name="number_of_bathrooms",nullable = false)
    private int numberOfBathrooms;

    @Column(name="util_square_meters", nullable = false)
    private float utilSquareMeters;

    @Column(name="total_square_meters", nullable = false)
    private float totalSquareMeters;

    @Column(name="construction_year", nullable = false)
    private int constructionYear;

    @ManyToOne
    @JoinColumn(name="house_type_id", nullable = false)
    private HouseTypeModel houseType;
}
