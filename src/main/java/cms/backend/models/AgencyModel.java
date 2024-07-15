package cms.backend.models;

import cms.backend.models.advert.AdvertModel;
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
@ToString
@NoArgsConstructor
@Table(name="agencies")
public class AgencyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @MapsId
    @JoinColumn(name="user_id")
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private UserModel user;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private Set<AdvertModel> adverts = new HashSet<>();

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private Set<SaleModel> sales = new HashSet<>();
}
