package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/")
@Controller
public class UserController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }



    @GetMapping("admin")
    public String allUsers() {
        return "adminpage";
    }

    @GetMapping("user")
    public String getUser(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        User newUser = new User();
        model.addAttribute("currentUser", user);
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());

        return "userpage";
    }

}

