package cms.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class AgencyResponseDto {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String avatarUri;
}
