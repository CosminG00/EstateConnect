package cms.backend.mappers;

import cms.backend.dtos.responses.UserResponseDto;
import cms.backend.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    UserResponseDto toDto(UserModel userModel);

    default UserDetails toUserDetails(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        return User
            .withUsername(userModel.getUsername())
            .password(userModel.getPassword())
            .authorities(Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + userModel.getRole().getName().toUpperCase()))
            )
            .build();
    }
}
