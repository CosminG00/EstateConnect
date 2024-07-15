package cms.backend.services;

import cms.backend.models.ChangePasswordModel;
import cms.backend.repositories.ChangePasswordRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {
    private final ChangePasswordRepository changePasswordRepository;
    private final Logger logger = LoggerFactory.getLogger(ChangeEmailService.class);

    public ChangePasswordModel create(ChangePasswordModel changePasswordModel) {
        this.logger.info("Creating change password for user with id {}", changePasswordModel.getUser().getId());
        return this.changePasswordRepository.save(changePasswordModel);
    }

    public Optional<ChangePasswordModel> getByToken(String token) {
        this.logger.info("Getting change email with token: {}", token);
        return this.changePasswordRepository.findByToken(token);
    }

    public ChangePasswordModel resolve(ChangePasswordModel changePasswordModel) {
        changePasswordModel.setResolvedAt(LocalDateTime.now());
        return this.changePasswordRepository.save(changePasswordModel);
    }
}