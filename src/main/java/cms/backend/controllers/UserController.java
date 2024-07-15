package cms.backend.controllers;

import cms.backend.dtos.requests.CreateFavoriteRequestDto;
import cms.backend.dtos.responses.AdvertResponseDto;
import cms.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/{userId}/favorites")
    public ResponseEntity<Void> addFavoriteAdvert(@PathVariable UUID userId, @RequestBody CreateFavoriteRequestDto favoriteRequestDto) {
        logger.info("Adding advert {} to user {} favorites", favoriteRequestDto.getAdvertId(), userId);
        userService.addFavoriteAdvert(userId, favoriteRequestDto.getAdvertId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/favorites")
    public ResponseEntity<Void> removeFavoriteAdvert(@PathVariable UUID userId, @RequestBody CreateFavoriteRequestDto favoriteRequestDto) {
        logger.info("Removing advert {} from user {} favorites", favoriteRequestDto.getAdvertId(), userId);
        userService.removeFavoriteAdvert(userId, favoriteRequestDto.getAdvertId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<Set<AdvertResponseDto>> getFavoriteAdverts(@PathVariable UUID userId) {
        logger.info("Retrieving favorite adverts for user {}", userId);
        Set<AdvertResponseDto> favorites = userService.getFavoriteAdverts(userId);
        return ResponseEntity.ok(favorites);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID userId) {
        logger.info("Deleting account for user {}", userId);
        userService.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }
}


