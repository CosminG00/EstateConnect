package cms.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResetPasswordRequestDto {
    private String token;
    private String newPassword;
}
