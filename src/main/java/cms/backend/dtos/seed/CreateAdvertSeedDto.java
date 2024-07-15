package cms.backend.dtos.seed;

import cms.backend.constants.AdvertTypeConstants;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "advertType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateLandSeedDto.class, name = AdvertTypeConstants.LAND),
        @JsonSubTypes.Type(value = CreateHouseSeedDto.class, name = AdvertTypeConstants.HOUSE),
        @JsonSubTypes.Type(value = CreateApartmentSeedDto.class, name = AdvertTypeConstants.APARTMENT)
})
public abstract class CreateAdvertSeedDto {
    private String title;
    private String description;
    private String address;
    private float price;
    private float totalSquareMeters;
    private String advertType;
    private String agencyName;
    private List<String> photosUris;
}
