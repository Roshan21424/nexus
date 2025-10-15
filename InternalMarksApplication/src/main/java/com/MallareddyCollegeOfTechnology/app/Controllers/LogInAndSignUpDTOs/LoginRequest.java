package com.MallareddyCollegeOfTechnology.app.Controllers.LogInAndSignUpDTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
    private String username;
    private String password;
}
