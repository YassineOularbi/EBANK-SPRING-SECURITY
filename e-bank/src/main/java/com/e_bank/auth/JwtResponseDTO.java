package com.e_bank.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDTO {
    private String accessToken;
}
