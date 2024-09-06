package rookies.MultiPath.core.auth.filter;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long accessTokenValidity = 1000L * 60 * 30; // 30분
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 14; // 14일
    private static final long TEMPORARY_TOKEN_EXPIRATION = 3 * 60 * 60 * 1000;  // 3시간 (밀리초 단위)

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Long userId, String username, String role, long validity) {
        return Jwts.builder()
                .claim("userId", String.valueOf(userId))
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(Long userId, String username, String role) {
        return generateToken(userId, username, role, accessTokenValidity);
    }

    public String generateRefreshToken(Long userId, String username, String role) {
        return generateToken(userId, username , role, refreshTokenValidity);
    }

    public Claims extractClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String getUsername(String token) {
        return extractClaims(token).get("username", String.class);
    }

    public String getRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public String getUserId(String token) {
        String userIdString = extractClaims(token).get("userId", String.class); // JWT에서 "userId" 클레임 추출
        return userIdString;
    }

    public String getGameId(String token) {
        return extractClaims(token).get("gameId", String.class);
    }

    // GUEST 사용자에게 임시 토큰 발급하는 메서드 추가
    public String generateTemporaryToken(String gameId) {
        String temporaryUserId = UUID.randomUUID().toString(); // 고유 식별자 생성
        return Jwts.builder()
                .claim("userId", temporaryUserId)
                .claim("username", "guest")
                .claim("role", "GUEST")
                .claim("gameId", gameId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TEMPORARY_TOKEN_EXPIRATION)) // 3시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 임시 토큰인지 확인하는 메서드 추가
    public boolean isTemporaryToken(String token) {
        return "guest".equals(getUsername(token)) && "GUEST".equals(getRole(token));
    }
}