package cms.backend.services;


import cms.backend.constants.AdvertTypeConstants;
import cms.backend.dtos.base.CreateAdvertTypeDto;
import cms.backend.dtos.responses.AdvertTypeResponseDto;
import cms.backend.exceptions.ConflictException;
import cms.backend.mappers.AdvertTypeMapper;
import cms.backend.models.advert.AdvertTypeModel;
import cms.backend.repositories.advert.AdvertTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertTypeService {
    private final AdvertTypeMapper advertTypeMapper;
    private final AdvertTypeRepository advertTypeRepository;
    private final Logger logger = LoggerFactory.getLogger(AdvertTypeService.class);

    public AdvertTypeModel createModel(CreateAdvertTypeDto createAdvertTypeDto) {
        final String name = createAdvertTypeDto.getName();

        this.logger.info("Attempt to create advert type with name {}", name);

        if (this.advertTypeRepository.findByName(name).isPresent()) {
            throw new ConflictException(AdvertTypeConstants.ALREADY_EXISTS_BY_NAME);
        }

        AdvertTypeModel advertTypeModel = this.advertTypeMapper.toModel(createAdvertTypeDto);

        return this.advertTypeRepository.save(advertTypeModel);
    }

    public AdvertTypeResponseDto createDto(CreateAdvertTypeDto createAdvertTypeDto) {
        AdvertTypeModel advertTypeModel = this.createModel(createAdvertTypeDto);
        return this.advertTypeMapper.toDto(advertTypeModel);
    }

    public Optional<AdvertTypeModel> getByNameModel(String name) {
        return this.advertTypeRepository.findByName(name);
    }
}
