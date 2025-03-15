package ru.y_lab.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Goal {
    private String id;
    private String userId;
    private String name;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;

}
