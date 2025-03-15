package ru.y_lab.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private boolean isAdmin;
}
