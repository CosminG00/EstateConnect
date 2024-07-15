package cms.backend.mappers;

import cms.backend.dtos.base.CreatePendingAgencyDto;
import cms.backend.models.PendingAgencyModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PendingAgencyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pendingUser.token", ignore = true)
    @Mapping(target = "pendingUser.username", ignore = true)
    @Mapping(target = "pendingUser.createdAt", ignore = true)
    @Mapping(target = "pendingUser.name", source = "name")
    @Mapping(target = "pendingUser.email", source = "email")
    @Mapping(target = "pendingUser.password", source = "password")
    @Mapping(target = "pendingUser.phoneNumber", source = "phoneNumber")
    PendingAgencyModel toModel(CreatePendingAgencyDto createPendingAgencyDto);

    default UserDetails toUserDetails(PendingAgencyModel pendingAgencyModel) {
        if (pendingAgencyModel == null) {
            return null;
        }

        return User
            .withUsername(pendingAgencyModel.getPendingUser().getUsername())
            .password(pendingAgencyModel.getPendingUser().getPassword())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_AGENCY")))
            .build();
    }
}
