package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@Controller
public class MVCConfig {

    @GetMapping("admin")
    public String admin() {
        return "adminpage";
    }
}
