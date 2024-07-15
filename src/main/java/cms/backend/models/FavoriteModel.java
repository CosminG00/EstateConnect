package cms.backend.models;

import cms.backend.models.ClientModel;
import cms.backend.models.advert.AdvertModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="favorites")
public class FavoriteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private ClientModel client;

    @ManyToOne
    @JoinColumn(name="advert_id", nullable = false)
    private AdvertModel advert;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public FavoriteModel(ClientModel client, AdvertModel advert) {
        this.client = client;
        this.advert = advert;
    }
}