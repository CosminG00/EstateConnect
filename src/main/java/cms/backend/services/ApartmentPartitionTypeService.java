package cms.backend.services;

import cms.backend.constants.AdvertTypeConstants;
import cms.backend.dtos.base.CreateApartmentPartitionTypeDto;

import cms.backend.dtos.responses.ApartmentPartitionTypeResponseDto;

import cms.backend.exceptions.ConflictException;
import cms.backend.mappers.ApartmentPartitionTypeMapper;

import cms.backend.models.apartment.ApartmentPartitionTypeModel;

import cms.backend.repositories.apartament.ApartmentPartitionTypeRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApartmentPartitionTypeService {
    private final ApartmentPartitionTypeMapper apartmentPartitionTypeMapper;
    private final ApartmentPartitionTypeRepository apartmentPartitionTypeRepository;
    private final Logger logger = LoggerFactory.getLogger(ApartmentPartitionTypeService.class);

    public ApartmentPartitionTypeModel createModel(CreateApartmentPartitionTypeDto createApartmentPartitionTypeDto) {
        final String name = createApartmentPartitionTypeDto.getName();

        this.logger.info("Attempt to create apartment partition type with name {}", name);

        if (this.apartmentPartitionTypeRepository.findByName(name).isPresent()) {
            throw new ConflictException(AdvertTypeConstants.ALREADY_EXISTS_BY_NAME);
        }

        ApartmentPartitionTypeModel apartmentPartitionTypeModel = this.apartmentPartitionTypeMapper.toModel(createApartmentPartitionTypeDto);

        return this.apartmentPartitionTypeRepository.save(apartmentPartitionTypeModel);
    }

    public ApartmentPartitionTypeResponseDto createDto(CreateApartmentPartitionTypeDto createApartmentPartitionTypeDto) {
        ApartmentPartitionTypeModel apartmentPartitionTypeModel = this.createModel(createApartmentPartitionTypeDto);
        return this.apartmentPartitionTypeMapper.toDto(apartmentPartitionTypeModel);
    }

    public Optional<ApartmentPartitionTypeModel> getByNameModel(String name) {
        return this.apartmentPartitionTypeRepository.findByName(name);
    }
}
