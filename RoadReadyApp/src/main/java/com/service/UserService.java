package com.service;

import com.Exception.ResourceNotFoundException;
import com.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserService {
    private final Session session;
    public UserService(Session session){
        this.session = session;
    }
    public void insert(User user) {
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
    }

    public void deleteUser(int id) {
        Transaction tx = session.beginTransaction();
        User user = session.find(User.class,id);
        if(user==null){
            throw new ResourceNotFoundException("Invalid Id given.");
        }
        session.createMutationQuery("delete from User where id=:id").setParameter("id",id).executeUpdate();
        tx.commit();
    }

    public List<User> getAllUsers(){
        Transaction tx = session.beginTransaction();
        List<User>list = session.createQuery("from User",User.class).list();
        tx.commit();
        return list;
    }

    public User getById(int id) {
        Transaction tx = session.beginTransaction();
        User user = session.find(User.class,id);
        tx.commit();
        return user;
    }
}
