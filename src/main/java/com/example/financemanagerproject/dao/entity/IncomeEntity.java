package com.example.financemanagerproject.dao.entity;

import com.example.financemanagerproject.dao.enums.IncomeSource;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private IncomeSource source;

    private BigDecimal amount;
    private String description;
    private LocalDate date;
}
