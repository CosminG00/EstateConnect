package cms.backend.dtos.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateHouseDto extends CreateAdvertDto {
    private int numberOfFloors;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private float utilSquareMeters;
    private float totalSquareMeters;
    private int constructionYear;
    private String houseType;
}
