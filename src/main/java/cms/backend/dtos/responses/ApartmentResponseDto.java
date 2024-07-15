package cms.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentResponseDto extends AdvertResponseDto {
    private int numberOfFloors;
    private int floorNumber;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private float utilSquareMeters;
    private int constructionYear;
    private ApartmentComfortTypeResponseDto comfortType;
    private ApartmentPartitionTypeResponseDto partitionType;
}
