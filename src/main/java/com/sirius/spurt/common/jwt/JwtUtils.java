package com.sirius.spurt.common.jwt;

import static com.sirius.spurt.common.meta.ResultCode.AUTHENTICATION_FAILED;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.common.jwt.token.AccessToken;
import com.sirius.spurt.common.jwt.token.RefreshToken;
import com.sirius.spurt.common.oauth.user.OAuthUser;
import com.sirius.spurt.store.provider.auth.AuthProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final AuthProvider authProvider;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public static final String ACCESS_TOKEN_NAME = "Authorization";
    public static final String REFRESH_TOKEN_NAME = "RefreshToken";
    public static final String TOKEN_TYPE = "Bearer%20";
    public static final String KEY_PREFIX = "jwt:";
    private final long ACCESS_TOKEN_EXPIRE_TIME = 10 * 60 * 1000L;
    private final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private AccessToken getAccessToken(String userId, String email) {
        String accessToken =
                Jwts.builder()
                        .setSubject("accessToken")
                        .claim("userId", userId)
                        .claim("email", email)
                        .setExpiration(new Date(getCurrentTimestamp() + ACCESS_TOKEN_EXPIRE_TIME))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

        return AccessToken.builder().token(accessToken).expireTime(ACCESS_TOKEN_EXPIRE_TIME).build();
    }

    private RefreshToken getRefreshToken(String userId, String email) {
        String refreshToken =
                Jwts.builder()
                        .setSubject("refreshToken")
                        .claim("userId", userId)
                        .claim("email", email)
                        .setExpiration(new Date(getCurrentTimestamp() + REFRESH_TOKEN_EXPIRE_TIME))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

        return RefreshToken.builder().token(refreshToken).expireTime(REFRESH_TOKEN_EXPIRE_TIME).build();
    }

    public void setAccessToken(HttpServletResponse response, String userId, String email) {
        AccessToken accessToken = getAccessToken(userId, email);
        setCookie(
                response,
                ACCESS_TOKEN_NAME,
                TOKEN_TYPE + accessToken.getToken(),
                accessToken.getExpireTime());
    }

    public void setRefreshToken(HttpServletResponse response, String userId, String email) {
        RefreshToken refreshToken = getRefreshToken(userId, email);
        authProvider.setRefreshToken(
                KEY_PREFIX + userId, refreshToken.getToken(), refreshToken.getExpireTime());
        setCookie(
                response,
                REFRESH_TOKEN_NAME,
                TOKEN_TYPE + refreshToken.getToken(),
                refreshToken.getExpireTime());
    }

    private void setCookie(HttpServletResponse response, String key, String token, Long expireTime) {
        ResponseCookie responseCookie =
                ResponseCookie.from(key, token)
                        .path("/")
                        .sameSite("None")
                        .httpOnly(false)
                        .secure(true)
                        .maxAge(Math.toIntExact(expireTime))
                        .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    public OAuthUser getOAuthUser(String token) {
        try {
            Claims claims =
                    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return OAuthUser.builder()
                    .userId(claims.get("userId").toString())
                    .email(claims.get("email").toString())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void updateTokens(HttpServletResponse response, String userId, String email) {
        if (!authProvider.hasRefreshToken(KEY_PREFIX + userId)) {
            throw new GlobalException(AUTHENTICATION_FAILED);
        }

        setAccessToken(response, userId, email);
        setRefreshToken(response, userId, email);
    }
}
