package cms.backend.mappers;

import cms.backend.dtos.base.CreateApartmentPartitionTypeDto;
import cms.backend.dtos.base.CreateHouseTypeDto;
import cms.backend.dtos.responses.ApartmentPartitionTypeResponseDto;
import cms.backend.dtos.responses.HouseTypeResponseDto;
import cms.backend.models.apartment.ApartmentPartitionTypeModel;
import cms.backend.models.house.HouseTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ApartmentPartitionTypeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartments", ignore = true)
    ApartmentPartitionTypeModel toModel(CreateApartmentPartitionTypeDto createApartmentPartitionTypeDto);

    ApartmentPartitionTypeResponseDto toDto(ApartmentPartitionTypeModel apartmentPartitionTypeModel);
}