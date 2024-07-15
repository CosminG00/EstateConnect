package cms.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LandResponseDto extends AdvertResponseDto{
    private float streetFront;
    private LandTypeResponseDto type;
    private LandPositionTypeResponseDto positionType;
}
