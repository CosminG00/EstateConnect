package cms.backend.security;

import cms.backend.exceptions.JwtAuthenticationException;
import cms.backend.exceptions.NotFoundException;
import cms.backend.mappers.UserMapper;
import cms.backend.models.UserModel;
import cms.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new JwtAuthenticationException("Invalid authentication class not instance of UsernamePasswordAuthenticationToken");
        }

        String token = (String) authentication.getCredentials();
        String username = jwtService.extractUsername(token);

        UserModel userModel = this.userService.getByUsernameModel(username);

        UserDetails userDetails = this.userMapper.toUserDetails(userModel);

        if (userDetails == null) {
            throw new NotFoundException("User details not found with the provided token");
        }

        if(!jwtService.isAccessTokenValid(token, userDetails)) {
            throw new JwtAuthenticationException("Invalid JWT token");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}