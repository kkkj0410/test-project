package sports_shop.project1.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import sports_shop.project1.domain.user.entity.Role;
import sports_shop.project1.security.model.Principal;
import sports_shop.project1.security.model.UserDetailsDto;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.key}")
    private String KEY;
    private SecretKey SECRET_KEY;
    private final long ACCESS_EXPIRE_TIME = 1000 * 60 * 30L;
    private final long REFRESH_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
    private final String KEY_ROLE = "role";
    private final String HEADER_TYPE = "typ";
    private final String HEADER_JWT_TYPE = "JWT";

    @PostConstruct
    private void setSecretKey() {
        if(KEY.length() < 32){
            log.info("JWT 키의 문자열 길이는 32 이상이어야 합니다.");
            throw new IllegalArgumentException("The secret key must be at least 32 bytes long.");
        }

        SECRET_KEY = Keys.hmacShaKeyFor(KEY.getBytes());
    }

    public String createToken(String username, Role role) {
        Date beginDate = new Date();
        Date endDate = new Date(beginDate.getTime() + ACCESS_EXPIRE_TIME);

        return Jwts.builder()
                .header()
                .add(HEADER_TYPE, HEADER_JWT_TYPE)
                .and()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(beginDate)
                .expiration(endDate)
                .signWith(new SecretKeySpec(SECRET_KEY.getEncoded(), "HmacSHA256"))
                .compact();
    }

    public String createToken(Authentication authentication) {
        Date beginDate = new Date();
        Date endDate = new Date(beginDate.getTime() + ACCESS_EXPIRE_TIME);

        //authentication.getName() is username
        log.info("authentication.getName() : {}", authentication.getName());

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .header()
                .add(HEADER_TYPE, HEADER_JWT_TYPE)
                .and()
                .subject(authentication.getName())
                .claim(KEY_ROLE, authorities)
                .issuedAt(beginDate)
                .expiration(endDate)
                .signWith(new SecretKeySpec(SECRET_KEY.getEncoded(), "HmacSHA256"))
                .compact();
    }

    public boolean isValid(String token) {

        Claims payload = tokenToClaims(token);

        try{
            if(payload != null && payload.getExpiration().after(new Date())){
                return true;
            }
        }catch(NullPointerException e){
            log.error("payload || getExpiration() is null");
            return false;
        }

        return false;
    }


    public boolean validateToken(String token) {
        try {

            Claims payload = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = tokenToClaims(token);
        if(claims == null){
            return null;
        }
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        log.info("authorities : {}", authorities);

        User principal = new User(claims.getSubject(), "",authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Collections.singletonList(new SimpleGrantedAuthority(
                claims.get(KEY_ROLE).toString()));
    }

    private Claims tokenToClaims(String token){
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }





}
