package cms.backend.services;

import cms.backend.constants.ClientConstants;
import cms.backend.dtos.responses.ClientResponseDto;
import cms.backend.enums.Role;
import cms.backend.exceptions.ConflictException;
import cms.backend.exceptions.NotFoundException;
import cms.backend.mappers.ClientMapper;
import cms.backend.models.ClientModel;
import cms.backend.models.RoleModel;
import cms.backend.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final RoleService roleService;
    private final UserService userService;
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public Optional<ClientResponseDto> getByEmail(String email) {
        this.logger.info("Getting client with email {}", email);
        return this.clientRepository
            .findByUserEmail(email)
            .map(this.clientMapper::toDto);
    }

    @Transactional
    public ClientModel create(ClientModel clientModel) {
        String email = clientModel.getUser().getEmail();

        this.logger.info("Attempt to create client with email {}", email);

        if(this.clientRepository.findByUserEmail(email).isPresent()) {
            throw new ConflictException(ClientConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        RoleModel roleModel = this.roleService.getOrCreate(Role.CLIENT.toString());

        clientModel.getUser().setRole(roleModel);

        return this.clientRepository.save(clientModel);
    }

    public ClientModel getByIdModel(UUID id) {
        this.logger.info("Getting client with id {}", id);
        return this.clientRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("There is no client with id " + id));
    }
}
