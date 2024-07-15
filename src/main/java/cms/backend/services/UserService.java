package cms.backend.services;

import cms.backend.dtos.base.UserChangeDetailsDto;
import cms.backend.dtos.responses.AdvertResponseDto;
import cms.backend.exceptions.NotFoundException;
import cms.backend.mappers.AdvertMapper;
import cms.backend.models.UserModel;
import cms.backend.models.advert.AdvertModel;
import cms.backend.models.advert.AdvertPhotoModel;
import cms.backend.repositories.UserRepository;
import cms.backend.repositories.advert.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AdvertRepository advertRepository; //nou
    private final AdvertMapper advertMapper; //nou
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);



    public void deleteAccount(UUID userId) {
        userRepository.deleteById(userId);
    }
    public Optional<UserModel> getByEmail(String email) {
        this.logger.info("Getting user with email {}", email);
        return this.userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserModel getByUsernameModel(String username) {
        this.logger.info("Getting user with username {}", username);
        UserModel user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("There is no user with username " + username));

        // Log individual fields to avoid LazyInitializationException
        this.logger.info("Retrieved user: id={}, username={}, phoneNumber={}, name={}, email={}, role={}",
                user.getId(), user.getUsername(), user.getPhoneNumber(), user.getName(), user.getEmail(),
                user.getRole() != null ? user.getRole().getName() : "null");

        return user;
    }

    //nou
    @Transactional
    public void addFavoriteAdvert(UUID userId, UUID advertId) {
        Optional<UserModel> userOpt = userRepository.findById(userId);
        Optional<AdvertModel> advertOpt = advertRepository.findById(advertId);
        if (userOpt.isPresent() && advertOpt.isPresent()) {
            UserModel user = userOpt.get();
            AdvertModel advert = advertOpt.get();
            user.getFavorites().add(advert);
            userRepository.save(user);
            logger.info("Added advert {} to user {} favorites", advertId, userId);
        }
    }
    @Transactional
    public void removeFavoriteAdvert(UUID userId, UUID advertId) {
        Optional<UserModel> userOpt = userRepository.findById(userId);
        Optional<AdvertModel> advertOpt = advertRepository.findById(advertId);
        if (userOpt.isPresent() && advertOpt.isPresent()) {
            UserModel user = userOpt.get();
            AdvertModel advert = advertOpt.get();
            user.getFavorites().remove(advert);
            userRepository.save(user);
            logger.info("Removed advert {} from user {} favorites", advertId, userId);
        }
    }
    public Set<AdvertResponseDto> getFavoriteAdverts(UUID userId) {
        Optional<UserModel> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            return user.getFavorites().stream()
                    .map(advert -> {
                        AdvertResponseDto dto = advertMapper.toDto(advert);
                        List<String> photoUris = advert.getAdvertPhotos().stream().map(AdvertPhotoModel::getUri).collect(Collectors.toList());
                        dto.setPhotoUris(photoUris);
                        return dto;
                    })
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }


    public Optional<UserModel> getByEmailModel(String email) {
        this.logger.info("Getting user with email {}", email);
        return this.userRepository.findByEmail(email);
    }

    public void updateEmailById(UUID id, String email) {
        this.logger.info("Updating email for user with id {}", id);
        this.userRepository.findById(id).map(existingUser -> {
            existingUser.setEmail(email);
            return this.userRepository.save(existingUser);
        }).orElseThrow(() -> new NotFoundException("There is no user with id " + id));
    }

    public void updatePasswordById(UUID id, String password, boolean hashPassword) {
        this.logger.info("Updating password for user with id {}", id);
        this.userRepository.findById(id).map(existingUser -> {
            String hashedPassword = hashPassword ? this.passwordEncoder.encode(password) : password;
            existingUser.setPassword(hashedPassword);
            return this.userRepository.save(existingUser);
        }).orElseThrow(() -> new NotFoundException("There is no user with id " + id));
    }

    public void updateAvatarUriById(UUID id, String avatarUri) {
        this.logger.info("Updating avatar URI for user with id {}", id);
        this.userRepository.findById(id).map(existingUser -> {
            existingUser.setAvatarUri(avatarUri);
            return this.userRepository.save(existingUser);
        }).orElseThrow(() -> new NotFoundException("There is no user with id " + id));
    }

    public void updateById(UUID id, UserChangeDetailsDto dto) {
        this.logger.info("Updating user with id {}", id);
        this.userRepository.findById(id).map(existingUser -> {
            existingUser.setName(dto.getName());
            return this.userRepository.save(existingUser);
        }).orElseThrow(() -> new NotFoundException("There is no user with id " + id));
    }
}
