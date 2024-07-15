//package cms.backend.services;
//
//import cms.backend.constants.AdvertConstants;
//import cms.backend.constants.AuthenticationConstants;
//import cms.backend.dtos.requests.CreateFavoriteRequestDto;
//import cms.backend.dtos.responses.AdvertResponseDto;
//import cms.backend.exceptions.NotFoundException;
//import cms.backend.exceptions.UnauthorizedException;
//import cms.backend.mappers.AdvertMapper;
//import cms.backend.models.ClientModel;
//import cms.backend.models.FavoriteModel;
//import cms.backend.models.UserModel;
//import cms.backend.models.advert.AdvertModel;
//import cms.backend.repositories.FavoriteRepository;
//import cms.backend.repositories.advert.AdvertRepository;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class FavoriteService {
//    private final UserService userService;
//    private final AdvertMapper advertMapper;
//    private final AdvertRepository advertRepository;
//    private final FavoriteRepository favoriteRepository;
//    private final Logger logger = LoggerFactory.getLogger(FavoriteService.class);
//
//    @Transactional
//    public void create(CreateFavoriteRequestDto requestDto) {
//        this.logger.info("Creating favorite for advert with id {}", requestDto.getAdvertId());
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if(!(principal instanceof UserDetails userDetails)) {
//
//            throw new UnauthorizedException(AuthenticationConstants.NOT_LOGGED_IN);
//        }
//
//        UserModel userModel = this.userService.getByUsernameModel(userDetails.getUsername());
//        this.logger.info("User found: {}", userModel.getUsername());
//
//        AdvertModel advert = this.advertRepository.findById(requestDto.getAdvertId())
//            .orElseThrow(() -> new NotFoundException(AdvertConstants.CREATED));
//
//        ClientModel client = userModel.getClient();
//        this.logger.info("Client found: {}", client.getUser().getName());
//
//        FavoriteModel favorite = new FavoriteModel(client, advert);
//
//
//        this.favoriteRepository.save(favorite);
//
//    }
//
//    @Transactional
//    public void deleteById(UUID advertId) {
//        this.logger.info("Deleting favorite for advert with id {}", advertId);
//        this.favoriteRepository.deleteByAdvertId(advertId);
//    }
//
//    public List<AdvertResponseDto> getAdverts() {
//        this.logger.info("Getting favorite adverts");
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if(!(principal instanceof UserDetails userDetails)) {
//            throw new UnauthorizedException(AuthenticationConstants.NOT_LOGGED_IN);
//        }
//
//        UserModel userModel = this.userService.getByUsernameModel(userDetails.getUsername());
//
//        ClientModel client = userModel.getClient();
//
//        return this.favoriteRepository
//            .findByClientId(client.getId())
//            .stream()
//            .map(FavoriteModel::getAdvert)
//            .map(this.advertMapper::toDto)
//            .collect(Collectors.toList());
//    }
//}
