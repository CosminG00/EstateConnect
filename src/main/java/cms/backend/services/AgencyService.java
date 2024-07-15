package cms.backend.services;

import cms.backend.constants.AgencyConstants;
import cms.backend.dtos.responses.AgencyResponseDto;
import cms.backend.enums.Role;
import cms.backend.exceptions.ConflictException;
import cms.backend.exceptions.NotFoundException;
import cms.backend.mappers.AgencyMapper;
import cms.backend.models.AgencyModel;
import cms.backend.models.RoleModel;
import cms.backend.repositories.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private final RoleService roleService;
    private final UserService userService;
    private final AgencyMapper agencyMapper;
    private final AgencyRepository agencyRepository;
    private final Logger logger = LoggerFactory.getLogger(AgencyService.class);

    public Optional<AgencyResponseDto> getByEmailDto(String email) {
        this.logger.info("Getting agency with email {}", email);
        return this.agencyRepository
            .findByUserEmail(email)
            .map(this.agencyMapper::toDto);
    }

    public Optional<AgencyModel> getByEmailModel(String email) {
        this.logger.info("Getting agency with email {}", email);
        return this.agencyRepository
            .findByUserEmail(email);
    }

    public Optional<AgencyModel> getByNameModel(String name) {
        this.logger.info("Getting agency with name {}", name);
        return this.agencyRepository
            .findByUserName(name);
    }

    public Optional<AgencyModel> getByUsernameModel(String username) {
        this.logger.info("Getting agency with username {}", username);
        return this.agencyRepository
            .findByUserUsername(username);
    }

    @Transactional
    public AgencyModel create(AgencyModel agencyModel) {
        String email = agencyModel.getUser().getEmail();

        this.logger.info("Attempt to create agency with email {}", email);

        if(this.agencyRepository.findByUserEmail(email).isPresent()) {
            throw new ConflictException(AgencyConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        RoleModel roleModel = this.roleService.getOrCreate(Role.CLIENT.toString());

        agencyModel.getUser().setRole(roleModel);

        return this.agencyRepository.save(agencyModel);
    }

    public AgencyModel getByIdModel(UUID id) {
        this.logger.info("Getting agency with id {}", id);
        return this.agencyRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("There is no agency with id " + id));
    }
}
