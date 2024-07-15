package cms.backend.services;

import cms.backend.constants.RoleConstants;
import cms.backend.dtos.base.CreateRoleDto;
import cms.backend.dtos.responses.RoleResponseDto;
import cms.backend.exceptions.ConflictException;
import cms.backend.mappers.RoleMapper;
import cms.backend.models.RoleModel;
import cms.backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public RoleModel getOrCreate(String name) {
    return this.roleRepository.findByName(name)
        .orElseGet(
            () -> this.createModel(new RoleModel(name))
        );
    }

    public RoleModel createModel(CreateRoleDto createRoleDto) {
        String name = createRoleDto.getName();

        this.logger.info("Attempt to create role with name {}", name);

        if (this.roleRepository.findByName(name).isPresent()) {
            throw new ConflictException(RoleConstants.ALREADY_EXISTS_BY_NAME);
        }

        RoleModel roleModel = this.roleMapper.toModel(createRoleDto);

        return this.roleRepository.save(roleModel);
    }

    public RoleModel createModel(RoleModel roleModel) {
        return this.roleRepository.save(roleModel);
    }

    public RoleResponseDto createDto(CreateRoleDto createRoleDto) {
        RoleModel roleModel = this.createModel(createRoleDto);
        return this.roleMapper.toDto(roleModel);
    }

    public Optional<RoleModel> getByNameModel(String name) {
        return this.roleRepository.findByName(name);
    }
}