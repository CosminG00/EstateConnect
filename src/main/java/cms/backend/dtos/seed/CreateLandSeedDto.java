package cms.backend.dtos.seed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLandSeedDto extends CreateAdvertSeedDto {
    private float streetFront;
    private String landType;
    private String positionType;
}
