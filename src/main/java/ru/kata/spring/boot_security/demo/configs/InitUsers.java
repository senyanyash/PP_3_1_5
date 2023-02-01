package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class InitUsers implements CommandLineRunner {

    private final UserService userService;
    private final EntityManager em;
    private final RoleService roleService;
    @Autowired
    public InitUsers(UserService userService, RoleService roleService, EntityManager em) {
        this.userService = userService;
        this.roleService = roleService;
        this.em = em;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role ROLE_USER = new Role("ROLE_USER");
        roleService.addRole(ROLE_USER);
        Role ROLE_ADMIN = new Role("ROLE_ADMIN");
        roleService.addRole(ROLE_ADMIN);
        User user = new User();
        user.setName("User");
        user.setLastName("Userovich");
        user.setAge(22);
        user.setCountry("Russia");
        user.setUsername("user@user.com");
        user.setUserpassword("user"); //user $2a$12$7tpF2m2ptr7STdTQzjrW6emqpOoxqo.jpuXzS0KdwXrs6XaAOUFrW
        user.setRoles(new HashSet<>(Arrays.asList(ROLE_USER)));
        userService.addUser(user);
        User admin = new User();
        admin.setName("Admin");
        admin.setLastName("Adminovich");
        admin.setAge(22);
        admin.setCountry("USA");
        admin.setUsername("admin@admin.com");
        admin.setUserpassword("admin"); //admin $2a$12$hx6glQx90Iks7yFrLf83au3CM.0uFidNxPxf246HjBe8/EKggM3oy
        admin.setRoles(new HashSet<>(Arrays.asList(ROLE_USER, ROLE_ADMIN)));
        userService.addUser(admin);
        User admin1 = new User();
        admin1.setName("Admin1");
        admin1.setLastName("Adminovich");
        admin1.setAge(22);
        admin1.setCountry("USA");
        admin1.setUsername("admin1@admin.com");
        admin1.setUserpassword("admin"); //admin $2a$12$hx6glQx90Iks7yFrLf83au3CM.0uFidNxPxf246HjBe8/EKggM3oy
        admin1.setRoles(new HashSet<>(Arrays.asList(ROLE_USER, ROLE_ADMIN)));
        userService.addUser(admin1);
    }
}
