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


//    @GetMapping("admin/new")
//    public String createUser(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "new";
//    }
    @PostMapping("/admin")
    public String addUser(@ModelAttribute("newUser") User user, BindingResult br, Model model, Principal principal) {
        User curUser = userService.getUserByUsername(principal.getName());
        User newUser = new User();
        User editUser = new User();
        model.addAttribute("editUser", editUser);
        model.addAttribute("currentUser", curUser);
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        if (br.hasErrors()) return "adminpage";
        userService.addUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin")
    public String allUsers(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        User newUser = new User();
        User editUser = new User();
        model.addAttribute("editUser", editUser);
        model.addAttribute("currentUser", user);
        model.addAttribute("newUser", newUser);
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "adminpage";
    }

//    @GetMapping("admin/{id}/edit")
//    public String getEditUser(@PathVariable("id") Long id, Model model) {
//        List<Role> roles = roleService.getAllRoles();
//        User user = userService.getUserById(id);
//        model.addAttribute("allRoles", roles);
//        model.addAttribute("user", user);
//        return "edit";
//    }

    @PatchMapping("admin/{id}")
    public String editUser(@ModelAttribute("editUser") User user, BindingResult br, Model model, Principal principal) {
        User curUser = userService.getUserByUsername(principal.getName());
        User newUser = new User();
        User editUser = new User();
        model.addAttribute("editUser", editUser);
        model.addAttribute("currentUser", curUser);
        model.addAttribute("newUser", newUser);
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        if (br.hasErrors()) return "adminpage";
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
        User user = userService.getUserByUsername(principal.getName());
        User newUser = new User();
        model.addAttribute("currentUser", user);
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());

        return "userpage";
    }

}

