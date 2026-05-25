package com.controller;

import com.Exception.ResourceNotFoundException;
import com.config.HibernateConfig;
import com.enums.Availability;
import com.enums.Reservation_Status;
import com.enums.Role;
import com.model.Car;
import com.model.Customer;
import com.model.Reservation;
import com.model.User;
import com.service.*;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import java.time.*;

import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
        HibernateConfig.getSessionFactory();
        System.out.println("Works");
        Session session = HibernateConfig.getSessionFactory().openSession();
        Scanner sc = new Scanner(System.in);
        ReservationService reservationService = new ReservationService(session);
        UserService userService = new UserService(session);
//        while(true){
//            System.out.println("1. Add User");
//            System.out.println("2. Delete User");
//            System.out.println("3. Fetch Users");
//            System.out.println("4. Update Users");
//            System.out.println("0. Exit");
//            int op = sc.nextInt();
//            if(op==0){
//                break;
//            }
//            switch(op){
//                case 1:
//                    User user = new User();
//                    user.setUser_name("Karthika");
//                    user.setPassword("kar@27");
//                    user.setEmail("1234@gmail.com");
//                    user.setAddress("CG,Salem");
//                    user.setMobile_no("126960648");
//                    user.setRole(Role.CUSTOMER);
//                    userService.insert(user);
//                    System.out.println("User Added");
//                    break;
//
//                case 2:
//                    System.out.println("Enter the user Id: ");
//                    int id = sc.nextInt();
//                    try{
//                        userService.deleteUser(id);
//                        System.out.println("User deleted Successfully.");
//                    }
//                    catch(ResourceNotFoundException e){
//                        System.out.println(e.getMessage());
//                    }
//                    break;
//
//                case 3:
//                    System.out.println("----------All Users-------------");
//                    List<User> list = userService.getAllUsers();
//                    list.forEach(System.out::println);
//                    break;
//
//                case 4:
//                    System.out.println("Enter the User Id to be updated");
//                    id = sc.nextInt();
//                    try{
//                        user = userService.getById(id);
//                        System.out.println("Existing user before update"+user);
//                        //sc.nextLine();
//                        System.out.println("Enter the new Username: ");
//                        sc.nextLine();
//                        user.setUser_name(sc.nextLine());
//                        System.out.println("Enter the new Password: ");
//                        user.setPassword(sc.nextLine());
//                        System.out.println("Enter the new Email: ");
//                        user.setEmail(sc.nextLine());
//                        System.out.println("Enter the new Address: ");
//                        user.setAddress(sc.nextLine());
//                        System.out.println("Enter the new Mobile: ");
//                        user.setMobile_no(sc.nextLine());
//                        System.out.println("Enter the new Role: ");
//                        user.setRole(Role.valueOf(sc.next()));
//                        userService.insert(user);
//                        System.out.println("After updation: "+user);
//                    }
//                    catch(ResourceNotFoundException e){
//                        System.out.println(e.getMessage());
//                    }
//                    break;
//
//                default:
//                    System.out.println("Invalid option");
//                    break;
//
//            }
//        }
        AuthService authService = new AuthService(session);
        System.out.println("---------ROAD READY LOGIN----------");
        System.out.println("Enter Username: ");
        String username = sc.next();
        System.out.println("Enter the password: ");
        String password  = sc.next();

        try{
            User user = authService.login(username,password);
            switch(user.getRole().toString()){
                case "CUSTOMER":
                    System.out.println("You are Customer.");
                    while(true){
                        System.out.println("1.Make Reservations");
                        System.out.println("2.View Cars");
                        System.out.println("3.Cancel Reservation");
                        System.out.println("4.Change Reservation");
                        System.out.println("0.Exit");
                        int op = sc.nextInt();
                        if(op==0){
                            break;
                        }
                        switch(op){
                            case 1:
                                System.out.println("RESERVE OUR BEST SUITED CAR FOR YOUR BEST JOURNEY");
                                Reservation reservation = new Reservation();
                                sc.nextLine();
                                System.out.println("Enter the Location: ");
                                reservation.setLocation(sc.nextLine());
                                System.out.println("Enter the Pickup Date: (yyyy-mm-dd)");
                                String p_date = sc.nextLine();
                                reservation.setP_date(LocalDate.parse(p_date));
                                System.out.println("Enter the Drop Date: (yyyy-mm-dd)");
                                String d_date = sc.nextLine();
                                reservation.setD_date(LocalDate.parse(d_date));
                                System.out.println("Enter the Car id: ");
                                int car_id = sc.nextInt();
                                Car car = session.find(Car.class,car_id);
                                if(car.getAvailability().equals(Availability.YES)){
                                    reservation.setCar(car);
                                    car.setAvailability(Availability.NO);
                                }
                                System.out.println("Reservation_status: ");
                                reservation.setReservation_status(Reservation_Status.valueOf(sc.next()));
                                reservationService.add(reservation,username);
                                System.out.println("Reserved Successfully..");
                                break;

                            case 2:
                                System.out.println("SELECT YOUR FAVOURITE CAR");
                                CarService carService = new CarService(session);
                                List<Car> list = carService.viewAllCars();
                                list.forEach(System.out::println);
                                break;

                            case 3:
                                System.out.println("CANCEL YOUR RESERVATION");
                                System.out.println("Enter your Reservation Id: ");
                                int res_id = sc.nextInt();
                                reservationService.cancelReservation(res_id);
                                System.out.println("Reservation Cancelled");
                                break;

                            case 4:
                                System.out.println("MAKE CHANGES IN YOUR RESERVATION");
                                System.out.println("Enter the Registration Id to be changed");
                                int u_res_id = sc.nextInt();
                                try {
                                    reservation = session.createQuery("from Reservation where id=:id", Reservation.class).setParameter("id", u_res_id).getSingleResult();
                                    System.out.println("Existing Reservation before change: " + reservation);
                                    System.out.println("Enter the Pickup Date: (yyyy-mm-dd)");
                                    sc.nextLine();
                                    String u_p_date = sc.nextLine();
                                    reservation.setP_date(LocalDate.parse(u_p_date));
                                    System.out.println("Enter the Drop Date: (yyyy-mm-dd)");
                                    String u_d_date = sc.nextLine();
                                    reservation.setD_date(LocalDate.parse(u_d_date));
                                    System.out.println("Enter the Car id: ");
                                    int u_car_id = sc.nextInt();
                                    Car u_car = session.find(Car.class,u_car_id);
                                    if(u_car.getAvailability().equals(Availability.YES)){
                                        reservation.setCar(u_car);
                                        u_car.setAvailability(Availability.NO);
                                    }
                                    System.out.println("Reservation_status: ");
                                    reservation.setReservation_status(Reservation_Status.valueOf(sc.next()));
                                    reservationService.add(reservation,username);
                                    System.out.println("Reserved Successfully..");
                                }
                                catch(ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;



                        }
                    }
                    break;
                case "ADMIN":
                    System.out.println("You are Admin: ");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
        catch(NoResultException e){
            System.out.println("Invalid Credentials");
        }
    }
}
