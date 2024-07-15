package cms.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthChangeEmailRequestDto {
    private String newEmail;
}
