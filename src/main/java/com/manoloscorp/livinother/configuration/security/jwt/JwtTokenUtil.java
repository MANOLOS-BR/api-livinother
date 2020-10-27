package com.manoloscorp.livinother.configuration.security.jwt;

import com.manoloscorp.livinother.configuration.security.services.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = 6982092698644334901L;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.jwtExpirationMs}")
  private int jwtExpirationMs;


  //retorna o username do token jwt
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  //retorna expiration date do token jwt
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  //para retornar qualquer informação do token nos iremos precisar da secret key
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  //check if the token has expired
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  //gera token para user
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
//    return doGenerateToken(claims, userDetails.getUsername());
    return doGenerateToken(claims, ((UserDetailsImpl) userDetails).getEmail());
  }

  //Cria o token e define tempo de expiração pra ele
  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
  }

  //valida o token
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(((UserDetailsImpl) userDetails).getEmail()) && !isTokenExpired(token));
  }

}
