package com.MallareddyCollegeOfTechnology.app.SecurityConfigurations;


import com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.JwtUtils.AuthEntryPoint;
import com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.JwtUtils.AuthTokenFilter;
import com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration //marking it as configuration(spring will pick it for configuration)
@EnableWebSecurity //enabling web security
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)//enabling method level security
public class SecurityConfiguration {


    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    public AuthEntryPoint authEntryPoint;
    @Autowired
    public AuthTokenFilter authTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //for every http request apply these filters
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {


       http.cors(withDefaults());

        /* defining csrf configuration */
        http.csrf( //Enables and configure csrf
                csrf->csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //store csrf token in cookie and do not allow js in client side to read
                        .ignoringRequestMatchers("/auth/**","/admin/**")); //ignore public urls

        /* defining authentication rules */
        http.authorizeHttpRequests( //apply authorisation rules
                requests -> requests
                        .requestMatchers("/auth/**","/admin/**") //if request matches this url
                        .permitAll() //then permit them all
                        .anyRequest() //if any other request
                        .authenticated()); //then authenticate them

            /* defining error handling rules */
            http.exceptionHandling( //apply custom exception handling
                    exception->exception
                            .authenticationEntryPoint(authEntryPoint)); //use the custom auth entry point  as authentication entry point

        /* adding authTokenFilter before usernamePasswordAuthentication filter */
        http.addFilterBefore( //add a filter before
                authTokenFilter,UsernamePasswordAuthenticationFilter.class); //add authToken filter before usernamePasswordAuthentication filter

        /* enabling form based login */
        http.formLogin(withDefaults()); //Enables form-based login

        /* enabling native-browser authentication popups */
        http.httpBasic(withDefaults()); //Enables HTTP Basic Authentication

        return http.build();

    }

}
