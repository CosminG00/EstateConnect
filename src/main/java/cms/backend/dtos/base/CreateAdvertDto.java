package cms.backend.dtos.base;

import cms.backend.constants.AdvertTypeConstants;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "advertType",
        visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CreateLandDto.class, name = AdvertTypeConstants.LAND),
    @JsonSubTypes.Type(value = CreateHouseDto.class, name = AdvertTypeConstants.HOUSE),
    @JsonSubTypes.Type(value = CreateApartmentDto.class, name = AdvertTypeConstants.APARTMENT)
})
public abstract class CreateAdvertDto {
    private String title;
    private String description;
    private float lat;
    private float lng;
    private String address;
    private float price;
    private float totalSquareMeters;
    private String advertType;
}
