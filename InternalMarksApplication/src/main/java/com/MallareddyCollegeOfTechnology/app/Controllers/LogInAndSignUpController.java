package com.MallareddyCollegeOfTechnology.app.Controllers;

import com.MallareddyCollegeOfTechnology.app.Controllers.LogInAndSignUpDTOs.LoginRequest;
import com.MallareddyCollegeOfTechnology.app.Controllers.LogInAndSignUpDTOs.LoginResponse;
import com.MallareddyCollegeOfTechnology.app.Controllers.LogInAndSignUpDTOs.SignupRequest;
import com.MallareddyCollegeOfTechnology.app.Controllers.LogInAndSignUpDTOs.UserInfoResponse;
import com.MallareddyCollegeOfTechnology.app.Entities.User;
import com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.JwtUtils.JwtUtils;
import com.MallareddyCollegeOfTechnology.app.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth") // Base URL
public class LogInAndSignUpController {



    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;


    @GetMapping("/csrf")
    public CsrfToken method1(HttpServletRequest request){
        return (CsrfToken)request.getAttribute(CsrfToken.class.getName());
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        if(signupRequest.getName()!=null && signupRequest.getPassword()!=null){

            /* create a new user */
            User user = new User();
            user.setName(signupRequest.getName());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setRole(User.Role.TEACHER);
            userService.saveUser(user);

        }
        return ResponseEntity.ok("Login successful");

    }

    //A public endpoint for everybody to signin
    //it will return a JWT token
    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication;

        try {
            System.out.println("A User is Trying to Login with username "+loginRequest.getUsername()+" with password "+loginRequest.getPassword());

            /* try to authenticate the user by the username and login */
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        } catch (AuthenticationException exception) {
            System.out.println("HERE");
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            map.put("error",exception.getMessage());
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        //set the authentication object  in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get the principle form the authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Collect roles from the extract the authorities for the principle
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //generate the jwt token using the details
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        // Prepare the response body, now including the JWT token directly in the body
        LoginResponse response = new LoginResponse( jwtToken,userDetails.getUsername(), roles);

        // Return the response entity with the JWT token included in the response body
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByName(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.isTwoFactorEnabled(),
                roles
        );

        return ResponseEntity.ok().body(response);
    }
}
