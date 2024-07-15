package cms.backend.models;

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
@Table(name="sales")
public class SaleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private ClientModel client;

    @ManyToOne
    @JoinColumn(name="agency_id", nullable = false)
    private AgencyModel agency;

    @Column(nullable = false)
    private LocalDateTime soldDate;

    @ManyToOne
    @JoinColumn(name="advert_id", nullable = false)
    private AdvertModel advert;

    @PrePersist
    public void prePersist() {
        this.soldDate = LocalDateTime.now();
    }
}