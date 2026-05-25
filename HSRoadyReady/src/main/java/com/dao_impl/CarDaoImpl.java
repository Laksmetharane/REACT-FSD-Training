package com.dao_impl;

import com.dao.CarDao;
import com.model.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarDaoImpl implements CarDao {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Car> findAll() {
        TypedQuery<Car> query;
        query = em.createQuery("from Car",Car.class);
        return query.getResultList();
    }
}
