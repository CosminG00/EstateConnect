package cms.backend.services;

import cms.backend.constants.AdvertConstants;
import cms.backend.constants.AdvertTypeConstants;
import cms.backend.constants.AgencyConstants;
import cms.backend.constants.AuthenticationConstants;
import cms.backend.dtos.base.CreateAdvertDto;
import cms.backend.dtos.base.CreateApartmentDto;
import cms.backend.dtos.base.CreateHouseDto;
import cms.backend.dtos.base.CreateLandDto;
import cms.backend.dtos.responses.*;
import cms.backend.dtos.seed.CreateAdvertSeedDto;
import cms.backend.dtos.seed.CreateApartmentSeedDto;
import cms.backend.dtos.seed.CreateHouseSeedDto;
import cms.backend.dtos.seed.CreateLandSeedDto;
import cms.backend.enums.AdvertType;
import cms.backend.exceptions.ConflictException;
import cms.backend.exceptions.NotFoundException;
import cms.backend.exceptions.UnauthorizedException;
import cms.backend.mappers.AdvertMapper;
import cms.backend.mappers.AdvertTypeMapper;
import cms.backend.mappers.ApartmentMapper;
import cms.backend.models.AgencyModel;
import cms.backend.models.advert.AdvertModel;
import cms.backend.models.advert.AdvertPhotoModel;
import cms.backend.models.advert.AdvertTypeModel;
import cms.backend.models.apartment.ApartmentModel;
import cms.backend.repositories.advert.AdvertRepository;
import cms.backend.repositories.apartament.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {
    @Lazy
    @Autowired
    private LandService landService;
    @Lazy
    @Autowired
    private HouseService houseService;
    @Lazy
    @Autowired
    private ApartmentService apartmentService;
    private final AdvertMapper advertMapper;
    private final AgencyService agencyService;
    private final AdvertRepository advertRepository;
    private final GeocodingService geocodingService;
    private final AdvertTypeService advertTypeService;
    private final AdvertPhotoService advertPhotoService;
    private final FileStorageService fileStorageService;

    private final Logger logger = LoggerFactory.getLogger(AdvertService.class);

    @Transactional
    public AdvertModel createRequestModel(CreateAdvertDto createAdvertDto, List<MultipartFile> files)
    {
        String title = createAdvertDto.getTitle();

        this.logger.info("Attempt to create advert with title {}", createAdvertDto.getTitle());

        if(this.advertRepository.findByTitle(title).isPresent()) {
            throw new ConflictException(AdvertConstants.ALREADY_EXISTS_BY_TITLE);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!(principal instanceof UserDetails userDetails)) {
            throw new UnauthorizedException(AuthenticationConstants.NOT_LOGGED_IN);
        }

        AgencyModel agencyModel = this.agencyService
            .getByUsernameModel(userDetails.getUsername())
            .orElseThrow(() -> new ConflictException(AgencyConstants.NOT_FOUND_BY_USERNAME));

        AdvertTypeModel advertTypeModel = this.advertTypeService
            .getByNameModel(createAdvertDto.getAdvertType())
            .orElseThrow(() -> new ConflictException(AdvertTypeConstants.NOT_FOUND_BY_NAME));

        AdvertModel advertModel = this.advertMapper.toModel(createAdvertDto);

        double latitude, longitude;
        String address;

        if (createAdvertDto.getLat() != 0 && createAdvertDto.getLng() != 0) {

            latitude = createAdvertDto.getLat();
            longitude = createAdvertDto.getLng();
            address = this.geocodingService.getAddressByCoords(latitude, longitude);
        } else if (createAdvertDto.getAddress() != null && !createAdvertDto.getAddress().isEmpty()) {

            double[] coords = this.geocodingService.getCoordsByAddress(createAdvertDto.getAddress());
            latitude = coords[0];
            longitude = coords[1];
            address = createAdvertDto.getAddress();
        } else {
            throw new IllegalArgumentException("Either coordinates or address must be provided");
        }

        System.out.println(address);

        advertModel.setLat((float) latitude);
        advertModel.setLng((float) longitude);
        advertModel.setAgency(agencyModel);
        advertModel.setAddress(address);
        advertModel.setAdvertType(advertTypeModel);

        advertModel = this.advertRepository.save(advertModel);

        for(MultipartFile file : files) {
            String fileName = this.fileStorageService.storeFile(file, false);
            this.advertPhotoService.createModel(new AdvertPhotoModel(fileName, advertModel));
        }

        return advertModel;
    }

    @Transactional
    public AdvertModel createSeedModel(CreateAdvertSeedDto createAdvertSeedDto)
    {
        String title = createAdvertSeedDto.getTitle();

        this.logger.info("Attempt to create advert seed with title {}", title);

        if(this.advertRepository.findByTitle(title).isPresent()) {
            throw new ConflictException(AdvertConstants.ALREADY_EXISTS_BY_TITLE);
        }

        AgencyModel agencyModel = this.agencyService
                .getByNameModel(createAdvertSeedDto.getAgencyName())
                .orElseThrow(() -> new NotFoundException(AgencyConstants.NOT_FOUND_BY_NAME));

        AdvertTypeModel advertTypeModel = this.advertTypeService
                .getByNameModel(createAdvertSeedDto.getAdvertType())
                .orElseThrow(() -> new ConflictException(AdvertTypeConstants.NOT_FOUND_BY_NAME));

        AdvertModel advertModel = this.advertMapper.toModel(createAdvertSeedDto);

        double[] coords = this.geocodingService.getCoordsByAddress(createAdvertSeedDto.getAddress());

        advertModel.setLat((float) coords[0]);
        advertModel.setLng((float) coords[1]);
        advertModel.setAgency(agencyModel);
        advertModel.setAdvertType(advertTypeModel);

        this.advertRepository.save(advertModel);

        for(String uri : createAdvertSeedDto.getPhotosUris()) {
            this.advertPhotoService.createModel(new AdvertPhotoModel(uri, advertModel));
        }

        return advertModel;
    }

    @Transactional
    public void createDto(CreateAdvertDto createAdvertDto, List<MultipartFile> files)
    {
        System.out.println("AdvertService.createDto called with: " + createAdvertDto);

        if (createAdvertDto.getAdvertType() == null) {
            throw new IllegalArgumentException("Advert type cannot be null");
        }

        switch (createAdvertDto.getAdvertType()) {
            case AdvertTypeConstants.LAND ->
                this.landService.createModel((CreateLandDto)createAdvertDto, files);
            case AdvertTypeConstants.HOUSE ->
                this.houseService.createModel((CreateHouseDto)createAdvertDto, files);
            case AdvertTypeConstants.APARTMENT ->
                this.apartmentService.createModel((CreateApartmentDto)createAdvertDto, files);
            default -> throw new NotFoundException(AdvertTypeConstants.NOT_FOUND_BY_NAME);
        }
    }

    @Transactional
    public void createSeed(CreateAdvertSeedDto createAdvertSeedDto)
    {
        System.out.println("AdvertService.createDto called with: " + createAdvertSeedDto);

        if (createAdvertSeedDto.getAdvertType() == null) {
            throw new IllegalArgumentException("Advert type cannot be null");
        }

        switch (createAdvertSeedDto.getAdvertType()) {
            case AdvertTypeConstants.LAND ->
                    this.landService.createSeed((CreateLandSeedDto)createAdvertSeedDto);
            case AdvertTypeConstants.HOUSE ->
                    this.houseService.createSeed((CreateHouseSeedDto) createAdvertSeedDto);
            case AdvertTypeConstants.APARTMENT ->
                    this.apartmentService.createSeed((CreateApartmentSeedDto)createAdvertSeedDto);
            default -> throw new NotFoundException(AdvertTypeConstants.NOT_FOUND_BY_NAME);
        }
    }

    public List<AdvertResponseDto> getAll() {
        this.logger.info("Attempt to retrieve all adverts from the database");

        List<AdvertResponseDto> dtos = new ArrayList<>();

        List<AdvertModel> adverts = advertRepository.findAll();

        for (AdvertModel advert : adverts) {
            AdvertResponseDto dto;
            switch(advert.getAdvertType().getName())
            {
                case AdvertTypeConstants.LAND -> {
                    dto = this.advertMapper.toLandDto(advert);
                }
                case AdvertTypeConstants.HOUSE -> {
                    dto = this.advertMapper.toHouseDto(advert);
                }
                case AdvertTypeConstants.APARTMENT -> {
                    dto = this.advertMapper.toApartmentDto(advert);
                }
                default -> {
                    throw new NotFoundException(AdvertTypeConstants.NOT_FOUND_BY_NAME);
                }
            }
            dto.setPhotoUris(advert.getAdvertPhotos().stream()
                    .map(AdvertPhotoModel::getUri)
                    .collect(Collectors.toList()));

            dtos.add(dto);
        }
        return dtos;
    }

    public AdvertResponseDto getById(UUID id) {
        this.logger.info("Fetching advert with id {}", id);
        AdvertModel advert = advertRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("There is no advert with id " + id));
        AdvertResponseDto dto;
        switch(advert.getAdvertType().getName())
        {
            case AdvertTypeConstants.LAND -> {
                dto = this.advertMapper.toLandDto(advert);
            }
            case AdvertTypeConstants.HOUSE -> {
                dto = this.advertMapper.toHouseDto(advert);
            }
            case AdvertTypeConstants.APARTMENT -> {
                dto = this.advertMapper.toApartmentDto(advert);
            }
            default -> {
                throw new NotFoundException(AdvertTypeConstants.NOT_FOUND_BY_NAME);
            }
        }
        dto.setPhotoUris(advert.getAdvertPhotos().stream()
            .map(AdvertPhotoModel::getUri)
            .collect(Collectors.toList()));



        return dto;
    }

    public Optional<AdvertModel> getByTitleModel(String title) {
        return this.advertRepository.findByTitle(title);
    }

    public void deleteAll() {
        this.advertRepository.deleteAll();
    }

//adaugat nou
    public List<AdvertResponseDto> getAdvertsByAgency(UUID agencyId) {
        List<AdvertModel> adverts = advertRepository.findByAgency_Id(agencyId);
        return adverts.stream()
                .map(advert -> {
                    AdvertResponseDto dto;
                    switch(advert.getAdvertType().getName()) {
                        case AdvertTypeConstants.LAND -> dto = advertMapper.toLandDto(advert);
                        case AdvertTypeConstants.HOUSE -> dto = advertMapper.toHouseDto(advert);
                        case AdvertTypeConstants.APARTMENT -> dto = advertMapper.toApartmentDto(advert);
                        default -> throw new NotFoundException(AdvertTypeConstants.NOT_FOUND_BY_NAME);
                    }
                    dto.setPhotoUris(advert.getAdvertPhotos().stream()
                            .map(AdvertPhotoModel::getUri)
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}