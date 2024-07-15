package cms.backend.mappers;

import cms.backend.dtos.base.CreateRoleDto;
import cms.backend.dtos.responses.RoleResponseDto;
import cms.backend.models.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    RoleModel toModel(CreateRoleDto createRoleDto);

    RoleResponseDto toDto(RoleModel roleModel);
}
