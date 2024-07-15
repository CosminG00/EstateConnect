package cms.backend.dtos.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLandDto extends CreateAdvertDto {
    private float streetFront;
    private String landType;
    private String positionType;
}
