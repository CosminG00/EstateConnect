package cms.backend.mappers;

import cms.backend.dtos.base.CreateApartmentDto;
import cms.backend.dtos.responses.ApartmentComfortTypeResponseDto;
import cms.backend.dtos.responses.ApartmentPartitionTypeResponseDto;
import cms.backend.dtos.responses.ApartmentResponseDto;
import cms.backend.dtos.seed.CreateApartmentSeedDto;
import cms.backend.models.advert.AdvertModel;
import cms.backend.models.apartment.ApartmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ApartmentMapper {
    @Mapping(target = "advert", ignore = true)
    @Mapping(target = "comfortType", ignore = true)
    @Mapping(target = "partitionType", ignore = true)
    ApartmentModel toModel(CreateApartmentDto createApartmentDto);

    @Mapping(target = "advert", ignore = true)
    @Mapping(target = "comfortType", ignore = true)
    @Mapping(target = "partitionType", ignore = true)
    ApartmentModel toModel(CreateApartmentSeedDto createApartmentSeedDto);
}
