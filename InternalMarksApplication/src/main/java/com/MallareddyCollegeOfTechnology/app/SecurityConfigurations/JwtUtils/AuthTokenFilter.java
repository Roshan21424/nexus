package com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.JwtUtils;

import com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.Services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.equals("/login") || path.startsWith("/public/")) {
            // Skip JWT check
            filterChain.doFilter(request, response);
            return;
        }

        try {
            /* Extract JWT from the header */
            String jwt = jwtUtils.getJwtFromHeader(request);

            /* if jwt token is not null and valid,create and authentication object and set it*/
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                String username = jwtUtils.getUserNameFromJwtToken(jwt); //get the username from the token

                UserDetails userDetails = userDetailsService.loadUserByUsername(username); //get the UserDetails object from userDetailsService


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( //create the authentication object
                        userDetails, //setting the userDetails object
                        null, //setting credentials
                        userDetails.getAuthorities() //setting authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication); //set the authentication object in security context
            } else {
                logger.warn("JWT is either null or invalid: {}", jwt);
            }
        } catch (Exception e) {
            logger.error("Exception in AuthTokenFilter: {}", e.getMessage(), e);
        }

        /* Proceed with the next filter in the chain */
        filterChain.doFilter(request, response);
    }
}
