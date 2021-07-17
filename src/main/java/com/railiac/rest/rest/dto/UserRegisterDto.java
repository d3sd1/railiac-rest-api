package com.railiac.rest.rest.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    private String email;
    private String pass;
    private String firstname;
    private String surname;
    private String referrercod;
}
