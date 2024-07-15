package cms.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthChangePasswordRequestDto {
    private String oldPassword;
    private String newPassword;
}
