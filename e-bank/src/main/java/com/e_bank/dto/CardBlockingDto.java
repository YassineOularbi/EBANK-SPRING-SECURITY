package com.e_bank.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardBlockingDto {
    private String BlockingReason;
}
