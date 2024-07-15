package cms.backend.mappers;

import cms.backend.dtos.responses.ClientResponseDto;
import cms.backend.models.ClientModel;
import cms.backend.models.PendingClientModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ClientMapper {
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.avatarUri", target = "avatarUri")
    @Mapping(source = "user.role.name", target = "role")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    ClientResponseDto toDto(ClientModel clientModel);

    @Mapping(source = "pendingUser.name", target = "user.name")
    @Mapping(source = "pendingUser.email", target = "user.email")
    @Mapping(source = "pendingUser.username", target = "user.username")
    @Mapping(source = "pendingUser.password", target = "user.password")
    @Mapping(target = "user.avatarUri", ignore = true)
    ClientModel toModel(PendingClientModel pendingClientModel);
}
