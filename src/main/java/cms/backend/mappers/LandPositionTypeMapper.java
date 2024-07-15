package cms.backend.mappers;

import cms.backend.dtos.base.CreateLandPositionTypeDto;
import cms.backend.dtos.base.CreateLandTypeDto;
import cms.backend.dtos.responses.LandPositionTypeResponseDto;
import cms.backend.dtos.responses.LandTypeResponseDto;
import cms.backend.models.land.LandPositionTypeModel;
import cms.backend.models.land.LandTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface LandPositionTypeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lands", ignore = true)
    LandPositionTypeModel toModel(CreateLandPositionTypeDto createLandPositionTypeDto);

    LandPositionTypeResponseDto toDto(LandPositionTypeModel landPositionTypeModel);
}