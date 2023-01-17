package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    public Role(String role) {
        this.name = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String role) {
        this.name = role;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
