package com.mysample.security;

import com.mysample.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author thiagofilgueira
 */
@Service
public class TokenAuthenticationService {

    private static UserDetailsServiceImpl userDetailsService;

    public TokenAuthenticationService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    static void addAuthentication(HttpServletResponse res, String username) {

        Claims claims = Jwts.claims().setSubject(username);

        if ( username != null && username.length() > 0 ) {
            // To extract the roles (String[])
            claims.put("roles", userDetailsService.getAclForExistingUser(username));
        }

        String JWT = Jwts.builder()
                .setClaims(claims)
                //.setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {

            // parse the token.
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody();

            // Extract the username
            String user = claims.getSubject();

            // Extract the Roles
            ArrayList<String> roles = (ArrayList<String>)claims.get("roles");

            // Then convert Roles to GrantedAuthority Object for injecting
            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if ( roles != null ) {
                for (String a : roles) {
                    SimpleGrantedAuthority g = new SimpleGrantedAuthority(a);
                    authorities.add(g);
                }
            }

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }
}