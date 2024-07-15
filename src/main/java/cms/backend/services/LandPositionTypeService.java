package cms.backend.services;

import cms.backend.constants.AdvertTypeConstants;

import cms.backend.dtos.base.CreateLandPositionTypeDto;

import cms.backend.dtos.responses.LandPositionTypeResponseDto;
import cms.backend.exceptions.ConflictException;

import cms.backend.mappers.LandPositionTypeMapper;

import cms.backend.models.land.LandPositionTypeModel;
import cms.backend.repositories.land.LandPositionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LandPositionTypeService {
    private final LandPositionTypeMapper landPositionTypeMapper;
    private final LandPositionTypeRepository landPositionTypeRepository;
    private final Logger logger = LoggerFactory.getLogger(LandPositionTypeService.class);

    public LandPositionTypeModel createModel(CreateLandPositionTypeDto createLandPositionTypeDto) {
        final String name = createLandPositionTypeDto.getName();

        this.logger.info("Attempt to create land position type with name {}", name);

        if (this.landPositionTypeRepository.findByName(name).isPresent()) {
            throw new ConflictException(AdvertTypeConstants.ALREADY_EXISTS_BY_NAME);
        }

        LandPositionTypeModel landPositionTypeModel = this.landPositionTypeMapper.toModel(createLandPositionTypeDto);

        return this.landPositionTypeRepository.save(landPositionTypeModel);
    }

    public LandPositionTypeResponseDto createDto(CreateLandPositionTypeDto createLandPositionTypeDto) {
        LandPositionTypeModel landPositionTypeModel = this.createModel(createLandPositionTypeDto);
        return this.landPositionTypeMapper.toDto(landPositionTypeModel);
    }

    public Optional<LandPositionTypeModel> getByNameModel(String name) {
        return this.landPositionTypeRepository.findByName(name);
    }
}
