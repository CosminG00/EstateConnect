package cms.backend.services;


import cms.backend.dtos.base.CreatePendingClientDto;
import cms.backend.enums.Role;
;
import cms.backend.models.PendingClientModel;
import cms.backend.models.PendingUserModel;
import cms.backend.repositories.PendingClientRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PendingClientService {
    private final EmailService emailService;
    private final PendingUserService pendingUserService;
    private final PendingClientRepository pendingClientRepository;
    private final Logger logger = LoggerFactory.getLogger(PendingClientService.class);

    public void create(CreatePendingClientDto createPendingClientDto) {
        final String email = createPendingClientDto.getEmail();

        this.logger.info("Creating pending client with email {}", email);

        PendingUserModel pendingUserModel = this.pendingUserService.create(createPendingClientDto, Role.CLIENT);

        PendingClientModel pendingClientModel = new PendingClientModel(pendingUserModel);

        this.pendingClientRepository.save(pendingClientModel);

        final String token = pendingUserModel.getToken();

        this.logger.info("Create pending client token: {}", token);

         this.emailService.sendClientActivationEmail(email, token);
    }


    public Optional<PendingClientModel> getByToken(String token) {
        this.logger.info("Getting pending client with token: {}", token);
        return this.pendingClientRepository.findByPendingUserToken(token);
    }

    public void delete(PendingClientModel pendingClientModel) {
        this.logger.info("Deleting pending client with email: {}", pendingClientModel.getPendingUser().getEmail());
        this.pendingClientRepository.delete(pendingClientModel);
    }

    public Optional<PendingClientModel> getByEmail(String email) {
        this.logger.info("Getting pending client with email: {}", email);
        return this.pendingClientRepository.findByPendingUserEmail(email);
    }
}
