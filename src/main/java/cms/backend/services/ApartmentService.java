package cms.backend.services;

import cms.backend.constants.ApartmentComfortTypeConstants;
import cms.backend.constants.ApartmentPartitionTypeConstants;

import cms.backend.dtos.base.CreateApartmentDto;
import cms.backend.dtos.seed.CreateApartmentSeedDto;
import cms.backend.exceptions.NotFoundException;
import cms.backend.mappers.ApartmentMapper;
import cms.backend.models.advert.AdvertModel;

import cms.backend.models.apartment.ApartmentComfortTypeModel;
import cms.backend.models.apartment.ApartmentModel;
import cms.backend.models.apartment.ApartmentPartitionTypeModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import cms.backend.repositories.apartament.ApartmentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    @Lazy
    @Autowired
    private  AdvertService advertService;
    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;
    private final ApartmentComfortTypeService apartmentComfortTypeService;
    private final ApartmentPartitionTypeService apartmentPartitionTypeService;
    private final Logger logger = LoggerFactory.getLogger(AdvertService.class);

    @Transactional
    public ApartmentModel createModel(CreateApartmentDto createApartmentDto, List<MultipartFile> files)
    {
        this.logger.info("Attempt to create apartment advert with title {}", createApartmentDto.getTitle());

        ApartmentComfortTypeModel comfortType = this.apartmentComfortTypeService
            .getByNameModel(createApartmentDto.getComfortType())
            .orElseThrow(() -> new NotFoundException(ApartmentComfortTypeConstants.NOT_FOUND_BY_NAME));

        ApartmentPartitionTypeModel partitionType = this.apartmentPartitionTypeService
            .getByNameModel(createApartmentDto.getPartitionType())
            .orElseThrow(() -> new NotFoundException(ApartmentPartitionTypeConstants.NOT_FOUND_BY_NAME));

        AdvertModel advertModel = this.advertService.createRequestModel(createApartmentDto, files);

        ApartmentModel apartmentModel = this.apartmentMapper.toModel(createApartmentDto);

        apartmentModel.setAdvert(advertModel);
        apartmentModel.setComfortType(comfortType);
        apartmentModel.setPartitionType(partitionType);

        return this.apartmentRepository.save(apartmentModel);
    }

    @Transactional
    public void createSeed(CreateApartmentSeedDto createApartmentSeedDto)
    {
        this.logger.info("Attempt to create seed apartment advert with title {}", createApartmentSeedDto.getTitle());

        ApartmentComfortTypeModel comfortType = this.apartmentComfortTypeService
            .getByNameModel(createApartmentSeedDto.getComfortType())
            .orElseThrow(() -> new NotFoundException(ApartmentComfortTypeConstants.NOT_FOUND_BY_NAME));

        ApartmentPartitionTypeModel partitionType = this.apartmentPartitionTypeService
            .getByNameModel(createApartmentSeedDto.getPartitionType())
            .orElseThrow(() -> new NotFoundException(ApartmentPartitionTypeConstants.NOT_FOUND_BY_NAME));

        AdvertModel advertModel = this.advertService.createSeedModel(createApartmentSeedDto);

        ApartmentModel apartmentModel = this.apartmentMapper.toModel(createApartmentSeedDto);

        apartmentModel.setAdvert(advertModel);
        apartmentModel.setComfortType(comfortType);
        apartmentModel.setPartitionType(partitionType);

        this.apartmentRepository.save(apartmentModel);
    }
}
