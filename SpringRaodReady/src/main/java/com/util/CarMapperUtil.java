package com.util;

import com.enums.Availability;
import com.model.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapperUtil implements RowMapper<Car> {
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car c = new Car();
        c.setId(rs.getInt("car_id"));
        c.setModel(rs.getString("model"));
        c.setAvailability(Availability.valueOf(rs.getString("availability")));
        c.setPrice(rs.getInt("price"));
        c.setNo_of_seats(rs.getInt("no_of_seats"));

        return c;
    }
}
