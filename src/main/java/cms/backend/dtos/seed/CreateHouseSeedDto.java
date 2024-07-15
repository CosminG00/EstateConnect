package cms.backend.dtos.seed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateHouseSeedDto extends CreateAdvertSeedDto {
    private int numberOfFloors;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private float utilSquareMeters;
    private float totalSquareMeters;
    private int constructionYear;
    private String houseType;
}
