package cms.backend.mappers;

import cms.backend.dtos.base.CreatePendingClientDto;
import cms.backend.models.PendingClientModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PendingClientMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pendingUser.token", ignore = true)
    @Mapping(target = "pendingUser.username", ignore = true)
    @Mapping(target = "pendingUser.createdAt", ignore = true)
    @Mapping(target = "pendingUser.name", source = "name")
    @Mapping(target = "pendingUser.email", source = "email")
    @Mapping(target = "pendingUser.password", source = "password")
    @Mapping(target = "pendingUser.phoneNumber", source = "phoneNumber")
    PendingClientModel toModel(CreatePendingClientDto createPendingClientDto);

    default UserDetails toUserDetails(PendingClientModel pendingClientModel) {
        if (pendingClientModel == null) {
            return null;
        }

        return User
            .withUsername(pendingClientModel.getPendingUser().getUsername())
            .password(pendingClientModel.getPendingUser().getPassword())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT")))
            .build();
    }
}
