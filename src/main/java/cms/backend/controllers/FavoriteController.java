//package cms.backend.controllers;
//
//import cms.backend.constants.FavoriteConstants;
//import cms.backend.dtos.requests.CreateFavoriteRequestDto;
//import cms.backend.responses.GetFavoriteAdvertsResponsePayload;
//import cms.backend.responses.MessageResponsePayload;
//import cms.backend.services.FavoriteService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import cms.backend.dtos.responses.AdvertResponseDto;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/favorites")
//@RequiredArgsConstructor
//public class FavoriteController {
//    private final FavoriteService favoriteService;
//
//    private final Logger logger = LoggerFactory.getLogger(FavoriteController.class);
//
//
//    @PostMapping
//    public ResponseEntity<MessageResponsePayload> create(@RequestBody CreateFavoriteRequestDto createFavoriteRequestDto) {
//        this.logger.info("Attempting to create favorite");
//        this.favoriteService.create(createFavoriteRequestDto);
//        MessageResponsePayload responsePayload = MessageResponsePayload.success(FavoriteConstants.CREATED);
//        return ResponseEntity.ok(responsePayload);
//    }
//
//    @DeleteMapping("/{advertId}")
//    public ResponseEntity<MessageResponsePayload> delete(@PathVariable UUID advertId) {
//        this.logger.info("Attempting to delete favorite by id");
//        this.favoriteService.deleteById(advertId);
//        MessageResponsePayload responsePayload = MessageResponsePayload.success(FavoriteConstants.DELETED_BY_ID);
//        return ResponseEntity.ok(responsePayload);
//    }
//
//    @GetMapping
//    public ResponseEntity<GetFavoriteAdvertsResponsePayload> getAdverts() {
//        this.logger.info("Attempting to get favorite adverts");
//        List<AdvertResponseDto> adverts = this.favoriteService.getAdverts();
//        GetFavoriteAdvertsResponsePayload responsePayload = new GetFavoriteAdvertsResponsePayload(adverts);
//        return ResponseEntity.ok(responsePayload);
//    }
//}
