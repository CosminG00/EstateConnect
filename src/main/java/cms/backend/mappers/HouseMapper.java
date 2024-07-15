package cms.backend.mappers;

import cms.backend.dtos.base.CreateHouseDto;
import cms.backend.dtos.seed.CreateHouseSeedDto;
import cms.backend.models.house.HouseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface HouseMapper {
    @Mapping(target = "advert", ignore = true)
    @Mapping(target = "houseType", ignore = true)
    HouseModel toModel(CreateHouseDto createHouseDto);

    @Mapping(target = "advert", ignore = true)
    @Mapping(target = "houseType", ignore = true)
    HouseModel toModel(CreateHouseSeedDto createHouseSeedDto);
}

