package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

public interface UserService {
    void addUser(User user);

    void removeUser(User user);


    void updateUser(User user);

    List<User> allUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);
}
