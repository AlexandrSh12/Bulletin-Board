package ru.shapovalov.NauJava.service;

import ru.shapovalov.NauJava.entity.User;

import java.util.Optional;

public interface UserService {
    void addUser(User user);
    Optional<User> getUserByUsername(String username);
}
