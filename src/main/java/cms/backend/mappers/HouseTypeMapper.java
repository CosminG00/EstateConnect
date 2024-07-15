package cms.backend.mappers;

import cms.backend.dtos.base.CreateHouseTypeDto;
import cms.backend.dtos.responses.HouseTypeResponseDto;
import cms.backend.models.house.HouseTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface HouseTypeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "houses", ignore = true)
    HouseTypeModel toModel(CreateHouseTypeDto createHouseTypeDto);

    HouseTypeResponseDto toDto(HouseTypeModel houseTypeModel);
}