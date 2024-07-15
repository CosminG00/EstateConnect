package cms.backend.models.advert;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(exclude = {"advert"})
@NoArgsConstructor
@Table(name="advert_photos")
public class AdvertPhotoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String uri;

    @ManyToOne
    @JoinColumn(name="advert_id", nullable = false)
    private AdvertModel advert;

    public AdvertPhotoModel(String uri, AdvertModel advert) {
        this.uri = uri;
        this.advert = advert;
    }
}
