package cms.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    // private final RoleService roleService;

//    @PostMapping
//    public ResponseEntity<RoleResponsePayload> create(@Valid @RequestBody RoleCreateRequestDto roleCreateRequestDto) {
//        RoleResponseDto roleResponseDto = this.roleService.create(roleCreateRequestDto);
//        URI location = LocationUtils.getLocationUri(roleResponseDto.getId());
//        RoleResponsePayload responsePayload = RoleResponsePayload.success(RoleConstants.CREATED_SUCCESS, roleResponseDto);
//        return ResponseEntity.created(location).body(responsePayload);
//    }
//
    @GetMapping
    public ResponseEntity<String> getAll() {
        // List<RoleResponseDto> roleResponseDtos = this.roleService.getAll();
        return ResponseEntity.ok("OK");
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<RoleResponseDto> getById(@PathVariable UUID id) {
//        return roleService.getById(id)
//            .map(ResponseEntity::ok)
//            .orElseThrow(() -> new NotFoundException(RoleConstants.NOT_FOUND_BY_ID));
//    }
//
//    @GetMapping("/name/{name}")
//    public ResponseEntity<RoleResponseDto> getByName(@PathVariable String name) {
//        return roleService.getByNameDto(name)
//            .map(ResponseEntity::ok)
//            .orElseThrow(() -> new NotFoundException(RoleConstants.NOT_FOUND_BY_NAME));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<RoleResponsePayload> update(
//        @PathVariable UUID id,
//        @Valid @RequestBody RoleUpdateRequestDto roleUpdateRequestDto
//    ) {
//        RoleResponseDto roleResponseDto = this.roleService.update(id, roleUpdateRequestDto);
//        RoleResponsePayload responsePayload = RoleResponsePayload.success(RoleConstants.UPDATED_SUCCESS, roleResponseDto);
//        return ResponseEntity.ok(responsePayload);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<MessageResponsePayload> deleteById(@PathVariable UUID id) {
//        this.roleService.deleteById(id);
//        MessageResponsePayload responsePayload = MessageResponsePayload.success(RoleConstants.DELETED_SINGLE_SUCCESS);
//        return ResponseEntity.ok(responsePayload);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<MessageResponsePayload> deleteAll() {
//        this.roleService.deleteAll();
//        MessageResponsePayload responsePayload = MessageResponsePayload.success(RoleConstants.DELETED_ALL_SUCCESS);
//        return ResponseEntity.ok(responsePayload);
//    }
}
