package cms.backend.services;

import cms.backend.models.PasswordResetModel;
import cms.backend.repositories.PasswordResetRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetRepository passwordResetRepository;
    private final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);

    public PasswordResetModel create(PasswordResetModel passwordResetModel) {
        this.logger.info("Creating password reset request for the user with email {}", passwordResetModel.getUser().getEmail());
        passwordResetModel = this.passwordResetRepository.save(passwordResetModel);
        return passwordResetModel;
    }

    public Optional<PasswordResetModel> getByUserEmail(String email) {
        this.logger.info("Getting password reset request for the user with email {}", email);
        return this.passwordResetRepository.findByUserEmail(email);
    }

    public Optional<PasswordResetModel> getByToken(String token) {
        this.logger.info("Getting password reset request for the token {}", token);
        return this.passwordResetRepository.findByToken(token);
    }

    public void delete(PasswordResetModel passwordResetModel) {
        this.logger.info("Deleting password reset request for the user with email {}", passwordResetModel.getUser().getEmail());
        this.passwordResetRepository.delete(passwordResetModel);
    }

    public void deleteByUserEmail(String email) {
        this.logger.info("Deleting all password reset requests for the user with email {}", email);
        this.passwordResetRepository.deleteByUserEmail(email);
    }
}
