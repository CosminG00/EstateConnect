package cms.backend.security;

import cms.backend.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) {
        final String token = JwtUtils.extractJwtFromCookie(request);

        if (token == null) {
            return;
        }

        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true); //inacesibil pt JavaScript
        accessTokenCookie.setSecure(true); //cookie e trimis doar cate HTTTPS
        accessTokenCookie.setPath("/"); // face cookie available
        accessTokenCookie.setMaxAge(0); // set cookie TO EXPIRE IMMEDIATELY
        response.addCookie(accessTokenCookie); // se adauga cookie la http
    }
}

