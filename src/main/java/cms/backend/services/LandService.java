package cms.backend.services;


import cms.backend.constants.LandPositionTypeConstants;
import cms.backend.constants.LandTypeConstants;

import cms.backend.dtos.base.CreateLandDto;
import cms.backend.dtos.seed.CreateLandSeedDto;
import cms.backend.exceptions.NotFoundException;

import cms.backend.mappers.LandMapper;
import cms.backend.models.advert.AdvertModel;

import cms.backend.models.land.LandModel;
import cms.backend.models.land.LandPositionTypeModel;
import cms.backend.models.land.LandTypeModel;
import cms.backend.repositories.land.LandRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandService {
    @Lazy
    @Autowired
    private AdvertService advertService;
    private final LandMapper landMapper;
    private final LandRepository landRepository;
    private final LandTypeService landTypeService;
    private final LandPositionTypeService landPositionTypeService;
    private final Logger logger = LoggerFactory.getLogger(AdvertService.class);

    @Transactional
    public LandModel createModel(CreateLandDto createLandDto, List<MultipartFile> files)
    {
        this.logger.info("Attempt to create land advert with title {}", createLandDto.getTitle());

        LandTypeModel landType = this.landTypeService
            .getByNameModel(createLandDto.getLandType())
            .orElseThrow(() -> new NotFoundException(LandTypeConstants.NOT_FOUND_BY_NAME));

        LandPositionTypeModel positionType = this.landPositionTypeService
            .getByNameModel(createLandDto.getPositionType())
            .orElseThrow(() -> new NotFoundException(LandPositionTypeConstants.NOT_FOUND_BY_NAME));

        AdvertModel advertModel = this.advertService.createRequestModel(createLandDto, files);

        LandModel landModel = this.landMapper.toModel(createLandDto);

        landModel.setType(landType);
        landModel.setAdvert(advertModel);
        landModel.setPositionType(positionType);

        return this.landRepository.save(landModel);
    }

    @Transactional
    public void createSeed(CreateLandSeedDto createLandSeedDto)
    {
        this.logger.info("Attempt to create seed land advert with title {}", createLandSeedDto.getTitle());

        LandTypeModel landType = this.landTypeService
            .getByNameModel(createLandSeedDto.getLandType())
            .orElseThrow(() -> new NotFoundException(LandTypeConstants.NOT_FOUND_BY_NAME));

        LandPositionTypeModel positionType = this.landPositionTypeService
            .getByNameModel(createLandSeedDto.getPositionType())
            .orElseThrow(() -> new NotFoundException(LandPositionTypeConstants.NOT_FOUND_BY_NAME));

        AdvertModel advertModel = this.advertService.createSeedModel(createLandSeedDto);

        LandModel landModel = this.landMapper.toModel(createLandSeedDto);

        landModel.setType(landType);
        landModel.setAdvert(advertModel);
        landModel.setPositionType(positionType);

        this.landRepository.save(landModel);
    }
}
