package cms.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ClientResponseDto {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String role;
    private String avatarUri;
    private String phoneNumber;

}
