package com.example.financemanagerproject.dto;

import com.example.financemanagerproject.dao.enums.OutcomeCategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutcomeResponseDto {
    private Long id;
    private OutcomeCategory category;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
}
