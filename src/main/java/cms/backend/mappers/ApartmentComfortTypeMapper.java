package cms.backend.mappers;

import cms.backend.dtos.base.CreateApartmentComfortTypeDto;
import cms.backend.dtos.base.CreateHouseTypeDto;
import cms.backend.dtos.responses.ApartmentComfortTypeResponseDto;
import cms.backend.dtos.responses.HouseTypeResponseDto;
import cms.backend.models.apartment.ApartmentComfortTypeModel;
import cms.backend.models.house.HouseTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ApartmentComfortTypeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartments", ignore = true)
    ApartmentComfortTypeModel toModel(CreateApartmentComfortTypeDto createApartmentComfortTypeDto);

    ApartmentComfortTypeResponseDto toDto(ApartmentComfortTypeModel apartmentComfortTypeModel);
}