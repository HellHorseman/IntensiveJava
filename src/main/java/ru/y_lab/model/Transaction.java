package ru.y_lab.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.y_lab.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Transaction {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private String description;
    private TransactionType type;

}
