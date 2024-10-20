package com.example.financemanagerproject.dao.entity;

import com.example.financemanagerproject.dao.enums.OutcomeCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "outcomes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutcomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OutcomeCategory category;

    private BigDecimal amount;
    private String description;
    private LocalDate date;
}
