package cms.backend.models;

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
@Table(name="change_emails")
public class ChangeEmailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String oldEmail;

    @Column(nullable = false)
    private String newEmail;

    @Column(nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime resolvedAt;

    public ChangeEmailModel(UserModel user, String token, String oldEmail, String newEmail) {
        this.user = user;
        this.token = token;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
    }
}