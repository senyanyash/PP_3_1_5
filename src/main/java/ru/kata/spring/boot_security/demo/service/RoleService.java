package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void addRole(Role role);
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}
