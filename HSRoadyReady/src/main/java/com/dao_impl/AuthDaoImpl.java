package com.dao_impl;

import com.dao.AuthDao;
import com.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class AuthDaoImpl implements AuthDao {
    @PersistenceContext
    private EntityManager em;
    @Override
    public User login(String username, String password) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.user_name=:user_name and u.password=:password",User.class);
        query.setParameter("user_name",username);
        query.setParameter("password",password);

        return query.getSingleResult();

    }
}
