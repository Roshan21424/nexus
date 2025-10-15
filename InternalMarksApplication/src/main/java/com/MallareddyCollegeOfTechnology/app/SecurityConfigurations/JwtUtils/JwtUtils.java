package com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.JwtUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;



@Component
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret; //jwt secret

    @Value("${spring.app.jwtExpirationsMS}")
    private int jwtExpirationMs; //jwt expiration

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getJwtFromHeader(HttpServletRequest request){

        String token = request.getHeader("Authorization"); //get the authorisation field from the request header

        /* if the token is not null and starts with Bearer,Remove Bearer and return the rest */
        if(token != null && token.startsWith("Bearer ")){
            System.out.println(token.substring(7)); //remove the "Bearer " part
            return token.substring(7);//return the rest
        }

        return null;
    }


    public String generateTokenFromUsername(UserDetails userDetails){

        String username = userDetails.getUsername(); //get the username

        /* generate token */
        return Jwts.builder()
                .subject(username) //setting subject as username
                .issuedAt(new Date()) //setting issue date
                .expiration(new Date((new Date()).getTime()+jwtExpirationMs)) //setting expiration time
                .signWith(key()) //signing the token with key
                .compact(); //build the token
    }

    public String getUserNameFromJwtToken(String token){

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            System.out.println("Error in JWT"+ e.getMessage());
        }
        return false;
    }

}


