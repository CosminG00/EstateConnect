package cms.backend.services;

import cms.backend.constants.PendingUserConstants;
import cms.backend.constants.UserConstants;
import cms.backend.dtos.base.CreatePendingUserDto;
import cms.backend.enums.Role;
import cms.backend.exceptions.ConflictException;
import cms.backend.mappers.PendingUserMapper;
import cms.backend.models.PendingUserModel;
import cms.backend.repositories.PendingUserRepository;
import cms.backend.security.JwtService;
import cms.backend.utils.GenerateUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PendingUserService {
    private final JwtService jwtService;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final PendingUserMapper pendingUserMapper;
    private final PendingUserRepository pendingUserRepository;
    private final Logger logger = LoggerFactory.getLogger(PendingClientService.class);

    public PendingUserModel create(CreatePendingUserDto createPendingUserDto, Role role) {
        final String email = createPendingUserDto.getEmail();

        this.logger.info("Creating pending user with email {}", email);

        if(this.pendingUserRepository.findByEmail(email).isPresent()) {
            throw new ConflictException(PendingUserConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        if(this.userService.getByEmail(email).isPresent()) {
            throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        PendingUserModel pendingUserModel = this.pendingUserMapper.toModel(createPendingUserDto);

        pendingUserModel.setRole(this.roleService.getOrCreate(role.toString()));
        pendingUserModel.setUsername(GenerateUtils.generateUsername(email));
        pendingUserModel.setPassword(passwordEncoder.encode(pendingUserModel.getPassword()));

        UserDetails userDetails = this.pendingUserMapper.toUserDetails(pendingUserModel, role);

        final String token;

        if(role.equals(Role.CLIENT)) {
            token = this.jwtService.generateActivateClientToken(userDetails);
        } else {
            token = this.jwtService.generateActivateAgencyToken(userDetails);
        }

        pendingUserModel.setToken(token);

        return this.pendingUserRepository.save(pendingUserModel);
    }

    public Optional<PendingUserModel> getByEmail(String email) {
        this.logger.info("Getting pending user with email: {}", email);
        return this.pendingUserRepository.findByEmail(email);
    }

    public Optional<PendingUserModel> getByToken(String token) {
        this.logger.info("Getting pending client with token: {}", token);
        return this.pendingUserRepository.findByToken(token);
    }

    public void delete(PendingUserModel pendingUserModel) {
        this.logger.info("Deleting pending user with email: {}", pendingUserModel.getEmail());
        this.pendingUserRepository.delete(pendingUserModel);
    }
}
