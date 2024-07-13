package com.e_bank.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private String name;
    private String mail;
    private String username;
    private String password;
    private String phone;
}
