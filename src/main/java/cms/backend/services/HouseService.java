package cms.backend.services;

import cms.backend.constants.HouseTypeConstants;
import cms.backend.dtos.base.CreateHouseDto;
import cms.backend.dtos.seed.CreateHouseSeedDto;
import cms.backend.exceptions.NotFoundException;
import cms.backend.mappers.HouseMapper;
import cms.backend.models.advert.AdvertModel;
import cms.backend.models.house.HouseModel;
import cms.backend.models.house.HouseTypeModel;
import cms.backend.repositories.house.HouseRepository;
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
public class HouseService {
    @Lazy
    @Autowired
    private AdvertService advertService;
    private final HouseMapper houseMapper;
    private final HouseRepository houseRepository;
    private final HouseTypeService houseTypeService;
    private final Logger logger = LoggerFactory.getLogger(AdvertService.class);

    @Transactional
    public HouseModel createModel(CreateHouseDto createHouseDto, List<MultipartFile> files)
    {
        this.logger.info("Attempt to create house advert with title {}", createHouseDto.getTitle());

        HouseTypeModel houseType = this.houseTypeService
            .getByNameModel(createHouseDto.getHouseType())
            .orElseThrow(() -> new NotFoundException(HouseTypeConstants.NOT_FOUND_BY_NAME));

        AdvertModel advertModel = this.advertService.createRequestModel(createHouseDto, files);

        HouseModel houseModel = this.houseMapper.toModel(createHouseDto);

        houseModel.setAdvert(advertModel);
        houseModel.setHouseType(houseType);

        return this.houseRepository.save(houseModel);
    }

    @Transactional
    public void createSeed(CreateHouseSeedDto createHouseSeedDto)
    {
        this.logger.info("Attempt to create seed house advert with title {}", createHouseSeedDto.getTitle());

        HouseTypeModel houseType = this.houseTypeService
            .getByNameModel(createHouseSeedDto.getHouseType())
            .orElseThrow(() -> new NotFoundException(HouseTypeConstants.NOT_FOUND_BY_NAME));

        AdvertModel advertModel = this.advertService.createSeedModel(createHouseSeedDto);

        HouseModel houseModel = this.houseMapper.toModel(createHouseSeedDto);

        houseModel.setAdvert(advertModel);
        houseModel.setHouseType(houseType);

        this.houseRepository.save(houseModel);
    }
}
