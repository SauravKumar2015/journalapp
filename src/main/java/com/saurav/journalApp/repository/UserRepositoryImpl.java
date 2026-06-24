package com.saurav.journalApp.repository;

import com.saurav.journalApp.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getUserForSA() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT DISTINCT u FROM User u JOIN u.roles r WHERE u.sentimentAnalysis = true AND r IN :roles",
                User.class);
        query.setParameter("roles", List.of("USER", "ADMIN"));

        return query.getResultList().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
                .toList();
    }

}
