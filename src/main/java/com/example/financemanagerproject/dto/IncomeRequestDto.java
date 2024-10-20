package com.example.financemanagerproject.dto;

import com.example.financemanagerproject.dao.enums.IncomeSource;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeRequestDto {
    private Long id;
    private IncomeSource source;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
}
