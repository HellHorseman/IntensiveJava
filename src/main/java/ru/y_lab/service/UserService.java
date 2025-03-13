package ru.y_lab.service;

import ru.y_lab.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users;

    public UserService() {
        users = new HashMap<>();
    }

    public boolean registerUser(String email, String password, String name) {
        if (users.containsKey(email)) {
            return false;
        }
        users.put(email, new User(email, password, name));
        return true;
    }

    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean updateUser(String email, String name, String password) {
        User user = users.get(email);
        if (user != null) {
            user.setName(name);
            user.setPassword(password);
            return true;
        }
        return false;
    }

    public boolean deleteUser(String email) {
        return users.remove(email) != null;
    }
}

