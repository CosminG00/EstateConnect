package cms.backend.controllers;

import cms.backend.constants.AdvertConstants;
import cms.backend.constants.AuthenticationConstants;
import cms.backend.dtos.base.CreateAdvertDto;
import cms.backend.dtos.responses.AdvertResponseDto;
import cms.backend.responses.CreateAdvertResponsePayload;
import cms.backend.responses.GetAllAdvertsResponsePayload;
import cms.backend.responses.MessageResponsePayload;
import cms.backend.services.AdvertService;
import cms.backend.utils.LocationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adverts")
public class AdvertController {
    private final AdvertService advertService;
    private static final Logger logger = LoggerFactory.getLogger(AdvertController.class);

    @GetMapping
    public ResponseEntity<GetAllAdvertsResponsePayload> getAll() {
        List<AdvertResponseDto> dtos = advertService.getAll();
        return ResponseEntity.ok(new GetAllAdvertsResponsePayload(dtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertResponseDto> getById(@PathVariable UUID id) {
        AdvertResponseDto dto = advertService.getById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "", consumes = "multipart/form-data")
    public ResponseEntity<MessageResponsePayload> create(
        @RequestPart("advert") String advertJson,
        @RequestPart("files") List<MultipartFile> files
    ) throws JsonProcessingException {
        CreateAdvertDto createAdvertDto = new ObjectMapper().readValue(advertJson, CreateAdvertDto.class);

        if (createAdvertDto.getAdvertType() == null) {
            throw new IllegalArgumentException("Advert type cannot be null");
        }

        this.advertService.createDto(createAdvertDto, files);
        MessageResponsePayload responsePayload = MessageResponsePayload.success(
            AdvertConstants.CREATED
        );
        return ResponseEntity.ok(responsePayload);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponsePayload> delete() {
        this.advertService.deleteAll();
        return ResponseEntity.ok(MessageResponsePayload.success(AdvertConstants.DELETED_ALL));
    }

    //adaugat nou
    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<List<AdvertResponseDto>> getByAgencyId(@PathVariable UUID agencyId) {
        System.out.println("Fetching adverts for agency: " + agencyId);
        List<AdvertResponseDto> adverts = advertService.getAdvertsByAgency(agencyId);
        System.out.println("Adverts fetched: " + adverts);
        return ResponseEntity.ok(adverts);
    }
}
