package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void addRole(Role role) {
        em.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return em.createQuery("from Role").getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        return (Role) em.createQuery("from User where id = :id").setParameter("id", id).getSingleResult();
    }
}
