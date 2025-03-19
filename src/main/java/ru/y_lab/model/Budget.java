package ru.y_lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    private Long id;
    private Long userId;
    private BigDecimal monthlyBudget;

}

