package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    void removeUser(User user);


    void updateUser(User user);

    List<User> allUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);



}
