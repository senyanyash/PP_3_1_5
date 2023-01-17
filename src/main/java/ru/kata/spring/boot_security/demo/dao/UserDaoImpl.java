package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;
    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void removeUser(User user) {
        User thisUser = em.merge(user);
        em.remove(thisUser);
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public List<User> allUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        Query query = em.createQuery("from User where username = :username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

}
