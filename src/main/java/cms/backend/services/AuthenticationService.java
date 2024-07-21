package cms.backend.services;

import cms.backend.constants.*;
import cms.backend.dtos.requests.*;
import cms.backend.dtos.responses.UserResponseDto;
import cms.backend.exceptions.NotFoundException;
import cms.backend.exceptions.UnauthorizedException;
import cms.backend.mappers.*;
import cms.backend.models.*;
import cms.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final ClientMapper clientMapper;
    private final AgencyMapper agencyMapper;
    private final ClientService clientService;
    private final AgencyService agencyService;
    private final PasswordEncoder passwordEncoder;
    private final PendingClientMapper pendingClientMapper;
    private final PendingAgencyMapper pendingAgencyMapper;
    private final PendingClientService pendingClientService;
    private final PendingAgencyService pendingAgencyService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Transactional
    public void register(AuthRegisterClientRequestDto requestDto) {
        this.logger.info("Registering client with email {}", requestDto.getEmail());
        this.pendingClientService.create(requestDto);
    }

    @Transactional
    public void register(AuthRegisterAgencyRequestDto requestDto) {
        this.logger.info("Registering agency with email {}", requestDto.getEmail());
        this.pendingAgencyService.create(requestDto);
    }

    @Transactional
    public void activate(AuthActivateClientRequestDto requestDto) {
        String token = requestDto.getToken();

        this.logger.info("Attempt to activate client with token {}", token);

        PendingClientModel pendingClientModel = this.pendingClientService.getByToken(token).orElseThrow(() ->
            new NotFoundException(PendingClientConstants.NOT_FOUND_BY_TOKEN)
        );

        UserDetails userDetails = this.pendingClientMapper.toUserDetails(pendingClientModel);

        if (!this.jwtService.isActivateClientTokenValid(token, userDetails)) {
            throw new UnauthorizedException(TokenConstants.INVALID);
        }

        ClientModel clientModel = this.clientMapper.toModel(pendingClientModel);

        this.clientService.create(clientModel);

        this.pendingClientService.delete(pendingClientModel);
    }

    @Transactional
    public void activate(AuthActivateAgencyRequestDto requestDto) {
        String token = requestDto.getToken();

        this.logger.info("Attempt to activate agency with token {}", token);

        PendingAgencyModel pendingAgencyModel = this.pendingAgencyService.getByToken(token).orElseThrow(() ->
            new NotFoundException(PendingAgencyConstants.NOT_FOUND_BY_TOKEN)
        );

        UserDetails userDetails = this.pendingAgencyMapper.toUserDetails(pendingAgencyModel);

        if (!this.jwtService.isActivateAgencyTokenValid(token, userDetails)) {
            throw new UnauthorizedException(TokenConstants.INVALID);
        }

        AgencyModel agencyModel = this.agencyMapper.toModel(pendingAgencyModel);

        this.agencyService.create(agencyModel);

        this.pendingAgencyService.delete(pendingAgencyModel);
    }

    @Transactional
    public String login(AuthLoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        this.logger.info("Attempt to login user with email {}", email);

        UserModel userModel = this.userService.getByEmail(email).orElseThrow(() ->
            new NotFoundException(UserConstants.NOT_FOUND_BY_EMAIL)
        );

        if(!this.passwordEncoder.matches(password, userModel.getPassword())) {
            this.logger.info("Password mismatch for user with email {}", email);
            throw new UnauthorizedException(AuthenticationConstants.INVALID_CREDENTIALS);
        }

        UserDetails userDetails = this.userMapper.toUserDetails(userModel);

        String token = this.jwtService.generateAccessToken(userDetails);
        this.logger.info("Generated token for user with email {}: {}", email, token);

        return token;
    }

    public UserResponseDto getLoggedInUserDto() {
        this.logger.info("Getting logged in user dto");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("SecurityContextHolder principal: {}", principal);

        if(!(principal instanceof UserDetails userDetails)) {
            logger.warn("Principal is not an instance of UserDetails: {}", principal);
            throw new UnauthorizedException(AuthenticationConstants.NOT_LOGGED_IN);
        }

        UserModel userModel = this.userService.getByUsernameModel(userDetails.getUsername());

        this.logger.info("Retrieved userModel with id={}, username={}, phoneNumber={}", userModel.getId(), userModel.getUsername(), userModel.getPhoneNumber());

        UserResponseDto userResponseDto = userMapper.toDto(userModel);

        this.logger.info("Mapped userResponseDto: {}", userResponseDto);

        return userResponseDto;
    }

}
