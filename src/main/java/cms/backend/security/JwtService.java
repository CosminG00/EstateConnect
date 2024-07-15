package cms.backend.security;

import cms.backend.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long accessTokenExpiration;

    @Value("${application.security.jwt.reset-password-token.expiration}")
    private long resetPasswordTokenExpiration;

    @Value("${application.security.jwt.activate-client-token.expiration}")
    private long activateClientTokenExpiration;

    @Value("${application.security.jwt.activate-agency-token.expiration}")
    private long activateAgencyTokenExpiration;

    @Value("${application.security.jwt.change-email-token.expiration}")
    private long changeEmailTokenExpiration;

    @Value("${application.security.jwt.change-password-token.expiration}")
    private long changePasswordTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", JwtConstants.TOKEN_TYPE_ACCESS);
        return buildToken(claims, userDetails, accessTokenExpiration);
    }

    public String generateChangeEmailToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", JwtConstants.TOKEN_TYPE_CHANGE_EMAIL);
        return buildToken(claims, userDetails, changeEmailTokenExpiration);
    }

    public String generateResetPasswordToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", JwtConstants.TOKEN_TYPE_RESET_PASSWORD);
        return buildToken(claims, userDetails, resetPasswordTokenExpiration);
    }

    public String generateChangePasswordToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", JwtConstants.TOKEN_TYPE_CHANGE_PASSWORD);
        return buildToken(claims, userDetails, changePasswordTokenExpiration);
    }

    public String generateActivateClientToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", JwtConstants.TOKEN_TYPE_ACTIVATE_CLIENT);
        return buildToken(claims, userDetails, activateClientTokenExpiration);
    }

    public String generateActivateAgencyToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", JwtConstants.TOKEN_TYPE_ACTIVATE_AGENCY);
        return buildToken(claims, userDetails, activateAgencyTokenExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private boolean isTokenValid(String token, UserDetails userDetails, String tokenType) {
        final String username = extractUsername(token);
        final String type = extractClaim(token, claims -> claims.get("token_type", String.class));
        return username.equals(userDetails.getUsername()) && type.equals(tokenType) && isTokenNotExpired(token);
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, JwtConstants.TOKEN_TYPE_ACCESS);
    }

    public boolean isChangeEmailTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, JwtConstants.TOKEN_TYPE_CHANGE_EMAIL);
    }

    public boolean isResetPasswordTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, JwtConstants.TOKEN_TYPE_RESET_PASSWORD);
    }

    public boolean isChangePasswordTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, JwtConstants.TOKEN_TYPE_CHANGE_PASSWORD);
    }

    public boolean isActivateClientTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, JwtConstants.TOKEN_TYPE_ACTIVATE_CLIENT);
    }

    public boolean isActivateAgencyTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, JwtConstants.TOKEN_TYPE_ACTIVATE_AGENCY);
    }

    private boolean isTokenNotExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}