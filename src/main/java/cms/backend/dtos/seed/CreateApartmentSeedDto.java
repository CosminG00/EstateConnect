package cms.backend.dtos.seed;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateApartmentSeedDto extends CreateAdvertSeedDto {
    private int numberOfFloors;
    private int numberOfRooms;
    private int floorNumber;
    private int numberOfBathrooms;
    private float utilSquareMeters;
    private int constructionYear;
    private String comfortType;
    private String partitionType;
}
