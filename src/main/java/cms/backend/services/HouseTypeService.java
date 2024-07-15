package cms.backend.services;

import cms.backend.constants.AdvertTypeConstants;
import cms.backend.dtos.base.CreateHouseTypeDto;
import cms.backend.dtos.responses.HouseTypeResponseDto;
import cms.backend.exceptions.ConflictException;
import cms.backend.mappers.HouseTypeMapper;
import cms.backend.models.house.HouseTypeModel;
import cms.backend.repositories.house.HouseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseTypeService {
    private final HouseTypeMapper houseTypeMapper;
    private final HouseTypeRepository houseTypeRepository;
    private final Logger logger = LoggerFactory.getLogger(HouseTypeService.class);

    public HouseTypeModel createModel(CreateHouseTypeDto createHouseTypeDto) {
        final String name = createHouseTypeDto.getName();

        this.logger.info("Attempt to create house type with name {}", name);

        if (this.houseTypeRepository.findByName(name).isPresent()) {
            throw new ConflictException(AdvertTypeConstants.ALREADY_EXISTS_BY_NAME);
        }

        HouseTypeModel houseTypeModel = this.houseTypeMapper.toModel(createHouseTypeDto);

        return this.houseTypeRepository.save(houseTypeModel);
    }

    public HouseTypeResponseDto createDto(CreateHouseTypeDto createHouseTypeDto) {
        HouseTypeModel houseTypeModel = this.createModel(createHouseTypeDto);
        return this.houseTypeMapper.toDto(houseTypeModel);
    }

    public Optional<HouseTypeModel> getByNameModel(String name) {
        return this.houseTypeRepository.findByName(name);
    }
}
