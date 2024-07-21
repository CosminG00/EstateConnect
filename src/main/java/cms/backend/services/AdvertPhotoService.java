package cms.backend.services;

import cms.backend.models.advert.AdvertPhotoModel;
import cms.backend.repositories.advert.AdvertPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdvertPhotoService {
    private final AdvertPhotoRepository advertPhotoRepository;
    private final Logger logger = LoggerFactory.getLogger(AdvertPhotoService.class);

    @Transactional
    public AdvertPhotoModel createModel(AdvertPhotoModel advertPhotoModel) {
        this.logger.info("Attempt to create advert photo with id");
        return this.advertPhotoRepository.save(advertPhotoModel);
    }
}
