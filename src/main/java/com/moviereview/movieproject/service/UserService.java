package com.moviereview.movieproject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class UserService {
//---------------------------------Constants & Fields-------------------------------------------------------------------
    private static final String SECRET_KEY ="3b7b6bb2ca15bde6ed35f056c713ac1f6b52e0da642302fef077555dfe39b5fc";

//------------------------------------------Token Generation------------------------------------------------------------
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }


    public String generateToken(
            Map<String , Object> extraClams,
            UserDetails userDetails){
        return  Jwts.builder()
                .setClaims(extraClams)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000 *60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();


    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
//-----------------------------------------Token Extraction-------------------------------------------------------------
    public String extractUserEmail(String token){
        return  extractClam(token, Claims::getSubject);
    }

    public <T> T extractClam(String token, Function<Claims,T> claimsResolver){
        final  Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Date extractExpiration(String token) {
        return  ( extractClam(token,Claims::getExpiration));
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
//----------------------------------------------Token Validation--------------------------------------------------------
    public boolean isTokenValid(String token , UserDetails userDetails){
        final String userName = extractUserEmail(token);
        return (userName.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }



}

