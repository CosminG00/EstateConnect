package cms.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AdvertResponseDto {
    private String id;
    private String title;
    private String description;
    private float lat;
    private float lng;
    private String address;
    private float price;
    private float totalSquareMeters;
    private String advertType;
    private List<String> photoUris;
    //private String agencyName; //adaugat nou
}
