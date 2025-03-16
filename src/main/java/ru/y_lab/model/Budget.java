package ru.y_lab.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Budget {
    private Long userId;
    private BigDecimal monthlyBudget;

}

