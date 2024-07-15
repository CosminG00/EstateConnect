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
@Table(name="change_passwords")
public class ChangePasswordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String oldPassword;

    @Column(nullable = false)
    private String newPassword;

    @Column(nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime resolvedAt;

    public ChangePasswordModel(UserModel user, String token, String oldPassword, String newPassword) {
        this.user = user;
        this.token = token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}