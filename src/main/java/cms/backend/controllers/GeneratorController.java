package cms.backend.controllers;

import cms.backend.responses.MessageResponsePayload;
import cms.backend.services.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/generate")
public class GeneratorController {
    private final GeneratorService generatorService;

    @PostMapping("/roles")
    public ResponseEntity<MessageResponsePayload> generateRoles() {
        MessageResponsePayload responsePayload = this.generatorService.generateRoles();
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/adverts")
    public ResponseEntity<MessageResponsePayload> generateAdverts() {
        MessageResponsePayload responsePayload = this.generatorService.generateAdverts();
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/house-types")
    public ResponseEntity<MessageResponsePayload> generateHouseTypes() {
        MessageResponsePayload responsePayload = this.generatorService.generateHouseTypes();
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/advert-types")
    public ResponseEntity<MessageResponsePayload> generateAdvertTypes() {
        MessageResponsePayload responsePayload = this.generatorService.generateAdvertTypes();
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/land-types")
    public ResponseEntity<MessageResponsePayload> generateLandTypes() {
        MessageResponsePayload responsePayload = this.generatorService.generateLandTypes();
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/land-position-types")
    public ResponseEntity<MessageResponsePayload> generateLandPositionTypes() {
        MessageResponsePayload responsePayload = this.generatorService.generateLandPositionTypes();
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/apartment-partition-types")
    public ResponseEntity<MessageResponsePayload> generateApartmentPartitionTypes() {
        MessageResponsePayload responsePayload = this.generatorService.generateApartmentPartitionTypes();
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/apartment-comfort-types")
    public ResponseEntity<MessageResponsePayload> generateApartmentComfortTypes() {
        MessageResponsePayload responsePayload = this.generatorService.generateApartmentComfortTypes();
        return ResponseEntity.ok(responsePayload);
    }
}
