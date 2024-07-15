package cms.backend.services;

import cms.backend.constants.*;
import cms.backend.dtos.base.*;
import cms.backend.dtos.seed.CreateAdvertSeedDto;
import cms.backend.enums.Severity;
import cms.backend.models.RoleModel;
import cms.backend.models.advert.AdvertModel;
import cms.backend.models.advert.AdvertTypeModel;
import cms.backend.models.apartment.ApartmentComfortTypeModel;
import cms.backend.models.apartment.ApartmentPartitionTypeModel;
import cms.backend.models.house.HouseTypeModel;
import cms.backend.models.land.LandPositionTypeModel;
import cms.backend.models.land.LandTypeModel;
import cms.backend.responses.MessageResponsePayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneratorService {
    private final RoleService roleService;
    private final AdvertService advertService;
    private final LandTypeService landTypeService;
    private final HouseTypeService houseTypeService;
    private final AdvertTypeService advertTypeService;
    private final LandPositionTypeService landPositionTypeService;
    private final ApartmentComfortTypeService apartmentComfortTypeService;
    private final ApartmentPartitionTypeService apartmentPartitionTypeService;
    private final Logger logger = LoggerFactory.getLogger(GeneratorService.class);

    public MessageResponsePayload generateRoles() {
        this.logger.info("Generating all roles from json file...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/roles.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<CreateRoleDto> createRoleDtos = mapper.readValue(inputStream, new TypeReference<>() {});

            int newRolesCount = 0;

            for (CreateRoleDto createRoleDto : createRoleDtos) {
                final String name = createRoleDto.getName();

                Optional<RoleModel> optionalRoleModel = this.roleService.getByNameModel(name);

                if(optionalRoleModel.isEmpty()) {
                    this.logger.info("Generating role with name {} from json file...", name);
                    // TODO - ValidationUtils.validate(createRoleDto);
                    // ValidationUtils.validate(roleSeedDto);
                    this.roleService.createDto(createRoleDto);
                    newRolesCount++;
                }
            }

            if (newRolesCount > 0) {
                this.logger.info(String.format("Generated %d new roles from json file.", newRolesCount));
            } else {
                this.logger.info("No new roles found to generate from json file.");
            }

            return new MessageResponsePayload(
                String.format(RoleConstants.GENERATED_SUCCESS, newRolesCount),
                Severity.SUCCESS
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MessageResponsePayload generateAdverts() {
        this.logger.info("Generating all adverts from json file...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/adverts.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<CreateAdvertSeedDto> createAdvertSeedDtos = mapper.readValue(inputStream, new TypeReference<>() {});

            int newAdvertsCount = 0;

            for (CreateAdvertSeedDto createAdvertSeedDto : createAdvertSeedDtos) {
                final String title = createAdvertSeedDto.getTitle();

                Optional<AdvertModel> optionalAdvertModel = this.advertService.getByTitleModel(title);

                if(optionalAdvertModel.isEmpty()) {
                    this.logger.info("Generating advert with title {} from json file...", title);
                    this.advertService.createSeed(createAdvertSeedDto);
                    newAdvertsCount++;
                }
            }
            if (newAdvertsCount > 0) {
                this.logger.info(String.format("Generated %d new adverts from json file.", newAdvertsCount));
            } else {
                this.logger.info("No new adverts found to generate from json file.");
            }

            return new MessageResponsePayload(
                String.format(AdvertConstants.GENERATED_SUCCESS, newAdvertsCount),
                Severity.SUCCESS
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MessageResponsePayload generateAdvertTypes() {
        this.logger.info("Generating all advert types from json file...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/advert_types.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<CreateAdvertTypeDto> createAdvertTypeDtos = mapper.readValue(inputStream, new TypeReference<>() {});

            int newAdvertTypesCount = 0;

            for (CreateAdvertTypeDto createAdvertTypeDto : createAdvertTypeDtos) {
                final String name = createAdvertTypeDto.getName();

                Optional<AdvertTypeModel> optionalAdvertTypeModel = this.advertTypeService.getByNameModel(name);

                if(optionalAdvertTypeModel.isEmpty()) {
                    this.logger.info("Generating advert type with name {} from json file...", name);
                    this.advertTypeService.createDto(createAdvertTypeDto);
                    newAdvertTypesCount++;
                }
            }

            if (newAdvertTypesCount > 0) {
                this.logger.info(String.format("Generated %d new advert types from json file.", newAdvertTypesCount));
            } else {
                this.logger.info("No new advert types found to generate from json file.");
            }

            return new MessageResponsePayload(
                String.format(AdvertTypeConstants.GENERATED_SUCCESS, newAdvertTypesCount),
                Severity.SUCCESS
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MessageResponsePayload generateHouseTypes() {
        this.logger.info("Generating all house types from json file...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/house_types.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<CreateHouseTypeDto> createHouseTypeDtos = mapper.readValue(inputStream, new TypeReference<>() {});

            int newHouseTypesCount = 0;

            for (CreateHouseTypeDto createHouseTypeDto : createHouseTypeDtos) {
                final String name = createHouseTypeDto.getName();

                Optional<HouseTypeModel> optionalHouseTypeModel = this.houseTypeService.getByNameModel(name);

                if(optionalHouseTypeModel.isEmpty()) {
                    this.logger.info("Generating house type with name {} from json file...", name);
                    this.houseTypeService.createDto(createHouseTypeDto);
                    newHouseTypesCount++;
                }
            }

            if (newHouseTypesCount > 0) {
                this.logger.info(String.format("Generated %d new advert types from json file.", newHouseTypesCount));
            } else {
                this.logger.info("No new advert types found to generate from json file.");
            }

            return new MessageResponsePayload(
                String.format(HouseTypeConstants.GENERATED_SUCCESS, newHouseTypesCount),
                Severity.SUCCESS
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MessageResponsePayload generateLandTypes() {
        this.logger.info("Generating all land types from json file...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/land_types.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<CreateLandTypeDto> createLandTypeDtos = mapper.readValue(inputStream, new TypeReference<>() {});

            int newLandTypesCount = 0;

            for (CreateLandTypeDto createLandTypeDto : createLandTypeDtos) {
                final String name = createLandTypeDto.getName();

                Optional<LandTypeModel> optionalLandTypeModel = this.landTypeService.getByNameModel(name);

                if(optionalLandTypeModel.isEmpty()) {
                    this.logger.info("Generating land type with name {} from json file...", name);
                    this.landTypeService.createDto(createLandTypeDto);
                    newLandTypesCount++;
                }
            }

            if (newLandTypesCount > 0) {
                this.logger.info(String.format("Generated %d new land types from json file.", newLandTypesCount));
            } else {
                this.logger.info("No new land types found to generate from json file.");
            }

            return new MessageResponsePayload(
                    String.format(LandTypeConstants.GENERATED_SUCCESS, newLandTypesCount),
                    Severity.SUCCESS
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public MessageResponsePayload generateLandPositionTypes() {
        this.logger.info("Generating all land position types from json file...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/land_position_types.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<CreateLandPositionTypeDto> createLandPositionTypeDtos = mapper.readValue(inputStream, new TypeReference<>() {});

            int newLandPositionTypesCount = 0;

            for (CreateLandPositionTypeDto createLandPositionTypeDto : createLandPositionTypeDtos) {
                final String name = createLandPositionTypeDto.getName();

                Optional<LandPositionTypeModel> optionalLandPositionTypeModel = this.landPositionTypeService.getByNameModel(name);

                if(optionalLandPositionTypeModel.isEmpty()) {
                    this.logger.info("Generating land position type with name {} from json file...", name);
                    this.landPositionTypeService.createDto(createLandPositionTypeDto);
                    newLandPositionTypesCount++;
                }
            }

            if (newLandPositionTypesCount > 0) {
                this.logger.info(String.format("Generated %d new land position types from json file.", newLandPositionTypesCount));
            } else {
                this.logger.info("No new land position types found to generate from json file.");
            }

            return new MessageResponsePayload(
                    String.format(LandPositionTypeConstants.GENERATED_SUCCESS, newLandPositionTypesCount),
                    Severity.SUCCESS
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public MessageResponsePayload generateApartmentComfortTypes() {
        this.logger.info("Generating all apartment comfort types from json file...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/apartment_comfort_types.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<CreateApartmentComfortTypeDto> createApartmentComfortTypeDtos = mapper.readValue(inputStream, new TypeReference<>() {});

            int newApartmentComfortTypesCount = 0;

            for (CreateApartmentComfortTypeDto createApartmentComfortTypeDto : createApartmentComfortTypeDtos) {
                final String name = createApartmentComfortTypeDto.getName();

                Optional<ApartmentComfortTypeModel> optionalApartmentComfortTypeModel = this.apartmentComfortTypeService.getByNameModel(name);

                if(optionalApartmentComfortTypeModel.isEmpty()) {
                    this.logger.info("Generating apartment comfort type with name {} from json file...", name);
                    this.apartmentComfortTypeService.createDto(createApartmentComfortTypeDto);
                    newApartmentComfortTypesCount++;
                }
            }

            if (newApartmentComfortTypesCount > 0) {
                this.logger.info(String.format("Generated %d new apartment comfort types from json file.", newApartmentComfortTypesCount));
            } else {
                this.logger.info("No new apartment comfort types found to generate from json file.");
            }

            return new MessageResponsePayload(
                    String.format(ApartmentComfortTypeConstants.GENERATED_SUCCESS, newApartmentComfortTypesCount),
                    Severity.SUCCESS
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public MessageResponsePayload generateApartmentPartitionTypes() {
        this.logger.info("Generating all apartment partition types from json file...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/apartment_partition_types.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<CreateApartmentPartitionTypeDto> createApartmentPartitionTypeDtos = mapper.readValue(inputStream, new TypeReference<>() {});

            int newApartmentPartitionTypesCount = 0;

            for (CreateApartmentPartitionTypeDto createApartmentPartitionTypeDto : createApartmentPartitionTypeDtos) {
                final String name = createApartmentPartitionTypeDto.getName();

                Optional<ApartmentPartitionTypeModel> optionalApartmentPartitionTypeModel = this.apartmentPartitionTypeService.getByNameModel(name);

                if(optionalApartmentPartitionTypeModel.isEmpty()) {
                    this.logger.info("Generating apartment partition type with name {} from json file...", name);
                    this.apartmentPartitionTypeService.createDto(createApartmentPartitionTypeDto);
                    newApartmentPartitionTypesCount++;
                }
            }

            if (newApartmentPartitionTypesCount > 0) {
                this.logger.info(String.format("Generated %d new apartment partition types from json file.", newApartmentPartitionTypesCount));
            } else {
                this.logger.info("No new apartment partiton types found to generate from json file.");
            }

            return new MessageResponsePayload(
                    String.format(ApartmentPartitionTypeConstants.GENERATED_SUCCESS, newApartmentPartitionTypesCount),
                    Severity.SUCCESS
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
