package cms.backend.models;

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
@Table(name="clients")
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @MapsId
    @JoinColumn(name="user_id")
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private UserModel user;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<SaleModel> sales = new HashSet<>();
}
