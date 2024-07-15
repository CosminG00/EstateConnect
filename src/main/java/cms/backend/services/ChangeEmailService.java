package cms.backend.services;

import cms.backend.models.ChangeEmailModel;
import cms.backend.repositories.ChangeEmailRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeEmailService {
    private final ChangeEmailRepository changeEmailRepository;
    private final Logger logger = LoggerFactory.getLogger(ChangeEmailService.class);

    public ChangeEmailModel create(ChangeEmailModel changeEmailModel) {
        this.logger.info("Creating change email for user with id {}", changeEmailModel.getUser().getId());
        return this.changeEmailRepository.save(changeEmailModel);
    }

    public Optional<ChangeEmailModel> getByToken(String token) {
        this.logger.info("Getting change email with token: {}", token);
        return this.changeEmailRepository.findByToken(token);
    }

    public ChangeEmailModel resolve(ChangeEmailModel changeEmailModel) {
        changeEmailModel.setResolvedAt(LocalDateTime.now());
        return this.changeEmailRepository.save(changeEmailModel);
    }
}
