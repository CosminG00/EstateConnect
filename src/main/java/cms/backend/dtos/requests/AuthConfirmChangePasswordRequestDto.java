package cms.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthConfirmChangePasswordRequestDto {
    private String token;
}