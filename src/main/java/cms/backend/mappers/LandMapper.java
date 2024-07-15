package cms.backend.mappers;

import cms.backend.dtos.base.CreateLandDto;
import cms.backend.dtos.seed.CreateLandSeedDto;
import cms.backend.models.land.LandModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface LandMapper {
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "advert", ignore = true)
    @Mapping(target = "positionType", ignore = true)
    LandModel toModel(CreateLandDto createLandDto);

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "advert", ignore = true)
    @Mapping(target = "positionType", ignore = true)
    LandModel toModel(CreateLandSeedDto createLandSeedDto);
}
