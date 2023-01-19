package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

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


    @GetMapping("admin/new")
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "new";
    }
    @PostMapping("/admin")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin")
    public String allUsers(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @GetMapping("admin/{id}/edit")
    public String getEditUser(@PathVariable("id") Long id, Model model) {
        List<Role> roles = roleService.getAllRoles();
        User user = userService.getUserById(id);
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", user);
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
    public String getUser(Model model, Principal principal) {
        User user = (User) userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

}

