package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class InitUsers implements CommandLineRunner {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public InitUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

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
        user.setUsername("user");
        user.setUserpassword("user");
        user.setRoles(Arrays.asList(ROLE_USER));
        userService.addUser(user);
        User admin = new User();
        admin.setName("Admin");
        admin.setLastName("Adminovich");
        admin.setAge(22);
        admin.setCountry("USA");
        admin.setUsername("admin");
        admin.setUserpassword("admin");
        admin.setRoles(Arrays.asList(ROLE_USER, ROLE_ADMIN));
        userService.addUser(admin);
    }
}
