package cms.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HouseResponseDto extends AdvertResponseDto{
    private int numberOfFloors;
    private int floorNumber;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private float utilSquareMeters;
    private int constructionYear;
    private HouseTypeResponseDto houseType;

}
