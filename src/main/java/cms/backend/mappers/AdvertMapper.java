package cms.backend.mappers;

import cms.backend.dtos.base.CreateAdvertDto;
import cms.backend.dtos.responses.AdvertResponseDto;
import cms.backend.dtos.responses.ApartmentResponseDto;
import cms.backend.dtos.responses.HouseResponseDto;
import cms.backend.dtos.responses.LandResponseDto;
import cms.backend.dtos.seed.CreateAdvertSeedDto;
import cms.backend.models.advert.AdvertModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AdvertMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agency", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "advertType", ignore = true)
    @Mapping(target = "advertPhotos", ignore = true)
    AdvertModel toModel(CreateAdvertDto createAdvertDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lat", ignore = true)
    @Mapping(target = "lng", ignore = true)
    @Mapping(target = "agency", ignore = true)
    @Mapping(target = "advertType", ignore = true)
    @Mapping(target = "advertPhotos", ignore = true)
    AdvertModel toModel(CreateAdvertSeedDto createAdvertSeedDto);

    @Mapping(target = "advertType", source ="advertType.name")
   // @Mapping(target = "agencyName", source = "agency.user.name") // Add this line
    AdvertResponseDto toDto(AdvertModel advertModel);

    @Mapping(target = "advertType", source ="advertType.name" )
    @Mapping(target = "numberOfFloors", source = "apartment.numberOfFloors")
    @Mapping(target = "floorNumber", source = "apartment.floorNumber")
    @Mapping(target = "numberOfRooms", source = "apartment.numberOfRooms")
    @Mapping(target = "numberOfBathrooms", source = "apartment.numberOfBathrooms")
    @Mapping(target = "utilSquareMeters", source = "apartment.utilSquareMeters")
    @Mapping(target = "constructionYear", source = "apartment.constructionYear")
    @Mapping(target = "comfortType.name", source = "apartment.comfortType.name")
    @Mapping(target = "partitionType.name", source = "apartment.partitionType.name")
    ApartmentResponseDto toApartmentDto(AdvertModel advertModel);

    @Mapping(target = "advertType", source ="advertType.name" )
    @Mapping(target = "numberOfFloors", source = "house.numberOfFloors")
    @Mapping(target = "numberOfRooms", source = "house.numberOfRooms")
    @Mapping(target = "numberOfBathrooms", source = "house.numberOfBathrooms")
    @Mapping(target = "utilSquareMeters", source = "house.utilSquareMeters")
    @Mapping(target = "constructionYear", source = "house.constructionYear")
    @Mapping(target = "houseType.name", source = "house.houseType.name")
    HouseResponseDto toHouseDto(AdvertModel advertModel);

    @Mapping(target = "advertType", source ="advertType.name" )
    @Mapping(target = "streetFront", source = "land.streetFront")
    @Mapping(target = "type.name", source = "land.type.name")
    @Mapping(target = "positionType.name", source = "land.positionType.name")
    LandResponseDto toLandDto(AdvertModel advertModel);

}
