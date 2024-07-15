package cms.backend.services;


import cms.backend.dtos.base.CreatePendingAgencyDto;

import cms.backend.enums.Role;

import cms.backend.models.PendingAgencyModel;
import cms.backend.models.PendingUserModel;
import cms.backend.repositories.PendingAgencyRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PendingAgencyService {
    private final EmailService emailService;
    private final PendingUserService pendingUserService;
    private final PendingAgencyRepository pendingAgencyRepository;
    private final Logger logger = LoggerFactory.getLogger(PendingAgencyService.class);

    public void create(CreatePendingAgencyDto createPendingAgencyDto) {
        final String email = createPendingAgencyDto.getEmail();

        this.logger.info("Creating pending agency with email {}", email);

        PendingUserModel pendingUserModel = this.pendingUserService.create(createPendingAgencyDto, Role.AGENCY);

        PendingAgencyModel pendingAgencyModel = new PendingAgencyModel(pendingUserModel);

        this.pendingAgencyRepository.save(pendingAgencyModel);

        final String token = pendingUserModel.getToken();

        this.logger.info("Create pending agency token: {}", token);

        //this.emailService.sendAgencyActivationEmail(email, token);
    }

    public Optional<PendingAgencyModel> getByEmail(String email) {
        this.logger.info("Getting pending agency with email: {}", email);
        return this.pendingAgencyRepository.findByPendingUserEmail(email);
    }

    public Optional<PendingAgencyModel> getByToken(String token) {
        this.logger.info("Getting pending agency with token: {}", token);
        return this.pendingAgencyRepository.findByPendingUserToken(token);
    }

    public void delete(PendingAgencyModel pendingAgencyModel) {
        this.logger.info("Deleting pending agency with email: {}", pendingAgencyModel.getPendingUser().getEmail());
        this.pendingAgencyRepository.delete(pendingAgencyModel);
    }
}
