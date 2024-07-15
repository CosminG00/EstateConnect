package cms.backend.dtos.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateApartmentDto extends CreateAdvertDto {
    private int numberOfFloors;
    private int numberOfRooms;
    private int floorNumber;
    private int numberOfBathrooms;
    private float utilSquareMeters;
    private int constructionYear;
    private String comfortType;
    private String partitionType;
}
