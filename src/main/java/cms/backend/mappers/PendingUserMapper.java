package cms.backend.mappers;

import cms.backend.dtos.base.CreatePendingUserDto;
import cms.backend.enums.Role;
import cms.backend.models.PendingUserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PendingUserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    PendingUserModel toModel(CreatePendingUserDto createPendingUserDto);

    default UserDetails toUserDetails(PendingUserModel pendingUserModel, Role role) {
        if (pendingUserModel == null) {
            return null;
        }

        return User
            .withUsername(pendingUserModel.getUsername())
            .password(pendingUserModel.getPassword())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())))
            .build();
    }
}
