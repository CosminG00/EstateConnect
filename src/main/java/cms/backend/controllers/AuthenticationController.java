package cms.backend.controllers;

import cms.backend.constants.AuthenticationConstants;
import cms.backend.dtos.requests.*;
import cms.backend.dtos.responses.UserResponseDto;
import cms.backend.responses.MessageResponsePayload;
import cms.backend.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    @Value("${application.security.jwt.expiration}")
    private long accessExpiration;

    private final AuthenticationService authenticationService;

    @PostMapping("/client/register")
    public ResponseEntity<MessageResponsePayload> register(
        @Validated @RequestBody AuthRegisterClientRequestDto requestDto
    ) {
        authenticationService.register(requestDto);
        MessageResponsePayload responsePayload = MessageResponsePayload.info(
            AuthenticationConstants.REGISTERED_SUCCESS
        );
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/agency/register")
    public ResponseEntity<MessageResponsePayload> register(
        @Validated @RequestBody AuthRegisterAgencyRequestDto requestDto
    ) {
        authenticationService.register(requestDto);
        MessageResponsePayload responsePayload = MessageResponsePayload.info(
            AuthenticationConstants.REGISTERED_SUCCESS
        );
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/client/activate")
    public ResponseEntity<MessageResponsePayload> activate(
        @Validated @RequestBody AuthActivateClientRequestDto requestDto
    ) {
        authenticationService.activate(requestDto);
        MessageResponsePayload responsePayload = MessageResponsePayload.info(
            AuthenticationConstants.ACTIVATE_CLIENT_SUCCESS
        );
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/agency/activate")
    public ResponseEntity<MessageResponsePayload> activate(
        @Validated @RequestBody AuthActivateAgencyRequestDto requestDto
    ) {
        authenticationService.activate(requestDto);
        MessageResponsePayload responsePayload = MessageResponsePayload.info(
            AuthenticationConstants.ACTIVATE_AGENCY_SUCCESS
        );
        return ResponseEntity.ok(responsePayload);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponsePayload> login(
        @Validated @RequestBody AuthLoginRequestDto requestDto,
        HttpServletResponse response
    ) {
        String accessToken = authenticationService.login(requestDto);

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) (accessExpiration / 1000));
        response.addCookie(accessTokenCookie);

        MessageResponsePayload responsePayload = MessageResponsePayload.success(
            AuthenticationConstants.LOGGED_IN_SUCCESS
        );

        return ResponseEntity.ok(responsePayload);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getLoggedUser() {
        UserResponseDto responsePayload = authenticationService.getLoggedInUserDto();
        return ResponseEntity.ok(responsePayload);
    }

    @GetMapping("/post-logout")
    public ResponseEntity<MessageResponsePayload> postLogoutHandler() {
        MessageResponsePayload responsePayload = MessageResponsePayload.success(
            AuthenticationConstants.LOGGED_OUT_SUCCESS
        );
        return ResponseEntity.ok(responsePayload);
    }

}
