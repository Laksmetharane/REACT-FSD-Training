package com;

import com.config.CarConfig;
import com.dao.CarDao;
import com.dao_impl.CarDaoImpl;
import com.enums.Availability;
import com.exceptions.ResourceNotFoundException;
import com.model.Car;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.util.Scanner;

public class MainCar {
    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CarConfig.class);
        System.out.println(context.getBean(DataSource.class));
        CarDao carDao = context.getBean(CarDaoImpl.class);
        carDao.insert(new Car("tesla", Availability.YES, 5000,6));

        //carDao.getAll().forEach(System.out::println);
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("1. Add Car");
            System.out.println("2. Delete Car by Id");
            System.out.println("3. Update Car");
            System.out.println("4. All Cars ");
            System.out.println("5. Get Car by id");
            System.out.println("0. Exit");
            int op = sc.nextInt();
            if(op == 0)
                break;
            switch(op){
                case 1:
                    carDao.insert(new Car("tesla", Availability.YES, 5000,6));
                    break;
                case 2:
                    System.out.println("Enter Id to delete car");
                    int id = sc.nextInt();
                    try {
                        carDao.deleteById(id);
                    }
                    catch(ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Enter car id to update");
                    try {
                        // verified the id
                        Car car = carDao.getById(sc.nextInt());
                        System.out.println("Existing car record ");
                        carDao.insert(car);
                        System.out.println(car);
                        // take new progress input

                        System.out.println("Enter Price to edit");
                        int price = sc.nextInt();
                        // attach new progress details to existing incident
                        car.setPrice(price);
                        // update the incident in DB
                        carDao.update(car);
                    }catch(EmptyResultDataAccessException e){
                        System.out.println("invalid id");
                    }
                    break;
                case 4:
                    carDao.getAll().forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("enter id to fetch record");
                    id = sc.nextInt();
                    try {
                        Car car = carDao.getById(id);
                        System.out.println(car);
                    }
                    catch(EmptyResultDataAccessException e){
                        System.out.println("invalid id");
                    }
                    break;
            } //switch ends

        }  //while ends
        context.close();
    }
}
