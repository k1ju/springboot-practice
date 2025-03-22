package me.shinsunyoung.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.User;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;
    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()) , user);
    }

    private String makeToken(Date expiry, User user){ // 토큰생성메서드
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더타입: JWT
                .setIssuer(jwtProperties.getIssuer()) // 내용 iss
                .setIssuedAt(now) // 내용 iat:현재시간
                .setExpiration(expiry) // 내용 exp: expiry 멤버 변숫값
                .setSubject(user.getEmail()) // 내용 sub : 유저의 이메일
                .setSubject(user.getEmail()) // 클레임 id : 유저 ID
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .compact();
    }

    public boolean validToken(String token){  //토큰 유효성 검증 메서드
        try{
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화.
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e){ // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }

    //토큰 기반으로 인증정보 가져오는 메서드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token); // 토큰 복호화한뒤 클레임을 갖오는 메서드
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(),"" ,authorities), token,authorities);
    }

    //토큰기반으로 유저ID 가져오는 메서드
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token){
        return Jwts.parser() // 클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
