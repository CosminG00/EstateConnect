package cms.backend.services;

import cms.backend.constants.AdvertTypeConstants;
import cms.backend.dtos.base.CreateApartmentComfortTypeDto;

import cms.backend.dtos.responses.ApartmentComfortTypeResponseDto;

import cms.backend.exceptions.ConflictException;
import cms.backend.mappers.ApartmentComfortTypeMapper;

import cms.backend.models.apartment.ApartmentComfortTypeModel;

import cms.backend.repositories.apartament.ApartmentComfortTypeRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApartmentComfortTypeService {
    private final ApartmentComfortTypeMapper apartmentComfortTypeMapper;
    private final ApartmentComfortTypeRepository apartmentComfortTypeRepository;
    private final Logger logger = LoggerFactory.getLogger(ApartmentComfortTypeService.class);

    public ApartmentComfortTypeModel createModel(CreateApartmentComfortTypeDto createApartmentComfortTypeDto) {
        final String name = createApartmentComfortTypeDto.getName();

        this.logger.info("Attempt to create apartment comfort type with name {}", name);

        if (this.apartmentComfortTypeRepository.findByName(name).isPresent()) {
            throw new ConflictException(AdvertTypeConstants.ALREADY_EXISTS_BY_NAME);
        }

        ApartmentComfortTypeModel apartmentComfortTypeModel = this.apartmentComfortTypeMapper.toModel(createApartmentComfortTypeDto);

        return this.apartmentComfortTypeRepository.save(apartmentComfortTypeModel);
    }

    public ApartmentComfortTypeResponseDto createDto(CreateApartmentComfortTypeDto createApartmentComfortTypeDto) {
        ApartmentComfortTypeModel apartmentComfortTypeModel = this.createModel(createApartmentComfortTypeDto);
        return this.apartmentComfortTypeMapper.toDto(apartmentComfortTypeModel);
    }

    public Optional<ApartmentComfortTypeModel> getByNameModel(String name) {
        return this.apartmentComfortTypeRepository.findByName(name);
    }
}
