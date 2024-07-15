package cms.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="pending_agencies")
public class PendingAgencyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @MapsId
    @JoinColumn(name="pending_user_id")
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private PendingUserModel pendingUser;

    public PendingAgencyModel(PendingUserModel pendingUser) {
        this.pendingUser = pendingUser;
    }
}
