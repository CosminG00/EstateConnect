package cms.backend.mappers;

import cms.backend.dtos.base.CreateAdvertTypeDto;
import cms.backend.dtos.responses.AdvertTypeResponseDto;
import cms.backend.models.advert.AdvertTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AdvertTypeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adverts", ignore = true)
    AdvertTypeModel toModel(CreateAdvertTypeDto createAdvertTypeDto);

     AdvertTypeResponseDto toDto(AdvertTypeModel advertTypeModel);
}