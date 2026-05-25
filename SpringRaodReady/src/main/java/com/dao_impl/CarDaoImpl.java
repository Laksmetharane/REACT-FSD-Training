package com.dao_impl;

import com.dao.CarDao;
import com.enums.Availability;
import com.exceptions.ResourceNotFoundException;
import com.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CarDaoImpl implements CarDao {
    private final JdbcTemplate jdbcTemplate;

    public CarDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Car> mapper(){
        return (rs,num)->{
            Car c = new Car();
            c.setId(rs.getInt("car_id"));
            c.setModel(rs.getString("model"));
            c.setAvailability(Availability.valueOf(rs.getString("availability")));
            c.setPrice(rs.getInt("price"));
            c.setNo_of_seats(rs.getInt("no_of_seats"));

            return c;
        };
    }
    @Override
    public void insert(Car car) {
        String sql = "insert into car(model,availability,price,no_of_seats)"+" values(?,?,?,?) ";
        jdbcTemplate.update(sql,car.getModel(),car.getAvailability().toString(),car.getPrice(),car.getNo_of_seats());
        System.out.println("Car added...");
    }

    @Override
    public List<Car> getAll() {
        String sql = "select * from car";
        return jdbcTemplate.query(sql,(rs,rowNum)->{
            Car c = new Car();
            c.setId(rs.getInt("car_id"));
            c.setModel(rs.getString("model"));
            c.setAvailability(Availability.valueOf(rs.getString("availability")));
            c.setPrice(rs.getInt("price"));
            c.setNo_of_seats(rs.getInt("no_of_seats"));

            return c;
        });
    }

    @Override
    public Car getById(int id) throws ResourceNotFoundException {
        String sql = "select * from car where car_id=?";
        return jdbcTemplate.queryForObject(sql,mapper(),id);
    }

    @Override
    public void deleteById(int id) throws ResourceNotFoundException {
         String sql = "delete from car where car_id=?";
         int numRow = jdbcTemplate.update(sql,id);
         if(numRow==0){
             throw new ResourceNotFoundException("Invalid id");
         }
         System.out.println("Car deleted");
    }

    @Override
    public void update(Car car) throws ResourceNotFoundException {
    String sql = "update car SET price =? where car_id=?";
    jdbcTemplate.update(sql,car.getPrice(),car.getId());
    System.out.println("Record updated");
    }
}
