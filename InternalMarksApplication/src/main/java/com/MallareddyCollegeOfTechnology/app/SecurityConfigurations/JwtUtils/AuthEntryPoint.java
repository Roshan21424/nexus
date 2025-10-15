package com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.JwtUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//for any url to whom authentication is required:
  //The user didn’t include any credentials (no token, no login).
  //The user’s credentials are invalid (bad token, expired token, wrong password).
  //The authentication process itself throws an error.

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        /* set response properties */
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //setting the content type for the response("application/json")
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //setting the status type for the response(401)

        /* create the body for the response */
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED); //{status:401}
        body.put("error", "Unauthorized"); //{error:unauthorised}
        body.put("message", authException.getMessage()); //{message:....}
        body.put("path", request.getServletPath()); //{path:....}


        /* attach the body to the response using the ObjectMapper */
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body); //writing into the body
    }

}