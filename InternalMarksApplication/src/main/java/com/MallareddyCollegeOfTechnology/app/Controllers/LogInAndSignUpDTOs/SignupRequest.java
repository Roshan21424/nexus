package com.MallareddyCollegeOfTechnology.app.Controllers.LogInAndSignUpDTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignupRequest {
    private String name;
    private String password;
}
