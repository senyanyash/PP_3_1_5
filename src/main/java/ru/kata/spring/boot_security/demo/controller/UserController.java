package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/")
@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }


    @GetMapping("admin/new")
    public String createUser(@ModelAttribute("newUser") User user) {
        return "new";
    }
    @PostMapping("/admin")
    public String addUser(@ModelAttribute("newUser") User user) {
        user.addRole(new Role("ROLE_USER"));
        userService.addUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin")
    public String allUsers(ModelMap model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }


    @GetMapping("admin/{id}/edit")
    public String getEditUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("admin/{id}")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/{id}")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.removeUser(user);
        return "redirect:/admin";
    }

    @GetMapping("user")
    public String getUser(@ModelAttribute("user") User user, Principal principal) {
         user = (User) userService.loadUserByUsername(principal.getName());

        return "user";
    }

    @GetMapping("/reg")
    public String register(@ModelAttribute("user") User user) {
        return "reg";
    }
}

