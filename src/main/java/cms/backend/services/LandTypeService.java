package cms.backend.services;

import cms.backend.constants.AdvertTypeConstants;

import cms.backend.dtos.base.CreateLandTypeDto;

import cms.backend.dtos.responses.LandTypeResponseDto;
import cms.backend.exceptions.ConflictException;

import cms.backend.mappers.LandTypeMapper;

import cms.backend.models.land.LandTypeModel;

import cms.backend.repositories.land.LandTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LandTypeService {
    private final LandTypeMapper landTypeMapper;
    private final LandTypeRepository landTypeRepository;
    private final Logger logger = LoggerFactory.getLogger(LandTypeService.class);

    public LandTypeModel createModel(CreateLandTypeDto createLandTypeDto) {
        final String name = createLandTypeDto.getName();

        this.logger.info("Attempt to create land type with name {}", name);

        if (this.landTypeRepository.findByName(name).isPresent()) {
            throw new ConflictException(AdvertTypeConstants.ALREADY_EXISTS_BY_NAME);
        }

        LandTypeModel landTypeModel = this.landTypeMapper.toModel(createLandTypeDto);

        return this.landTypeRepository.save(landTypeModel);
    }

    public LandTypeResponseDto createDto(CreateLandTypeDto createLandTypeDto) {
        LandTypeModel landTypeModel = this.createModel(createLandTypeDto);
        return this.landTypeMapper.toDto(landTypeModel);
    }

    public Optional<LandTypeModel> getByNameModel(String name) {
        return this.landTypeRepository.findByName(name);
    }
}
