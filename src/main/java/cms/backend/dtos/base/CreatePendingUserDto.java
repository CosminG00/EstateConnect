package cms.backend.dtos.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreatePendingUserDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
