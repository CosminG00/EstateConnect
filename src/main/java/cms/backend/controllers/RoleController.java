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


    @GetMapping
    public ResponseEntity<String> getAll() {
        // List<RoleResponseDto> roleResponseDtos = this.roleService.getAll();
        return ResponseEntity.ok("OK");
    }

}
