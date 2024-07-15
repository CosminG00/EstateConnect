package cms.backend.dtos.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserChangeDetailsDto {
    private String name;
    private String phoneNumber;
}