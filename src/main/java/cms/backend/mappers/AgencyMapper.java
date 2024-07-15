package cms.backend.mappers;

import cms.backend.dtos.responses.AgencyResponseDto;
import cms.backend.models.AgencyModel;
import cms.backend.models.PendingAgencyModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AgencyMapper {
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.avatarUri", target = "avatarUri")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    AgencyResponseDto toDto(AgencyModel agencyModel);

    @Mapping(source = "pendingUser.name", target = "user.name")
    @Mapping(source = "pendingUser.email", target = "user.email")
    @Mapping(source = "pendingUser.username", target = "user.username")
    @Mapping(source = "pendingUser.password", target = "user.password")
    @Mapping(target = "user.avatarUri", ignore = true)
    @Mapping(target = "adverts", ignore = true)
    @Mapping(target = "sales", ignore = true)
    AgencyModel toModel(PendingAgencyModel pendingAgencyModel);
}
