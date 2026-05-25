package com;

import com.Exception.ResourceNotFoundException;
import com.config.AppConfig;
import com.dao.AuthDao;
import com.dao.CarDao;
import com.dao.CustomerDao;
import com.dao.ReservationDao;
import com.enums.Availability;
import com.enums.Reservation_Status;
import com.model.Car;
import com.model.Customer;
import com.model.Reservation;
import com.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AuthDao authDao = context.getBean(AuthDao.class);
        ReservationDao reservationDao = context.getBean(ReservationDao.class);
        CarDao carDao = context.getBean(CarDao.class);
        Scanner sc = new Scanner(System.in);
        System.out.println("-------WELCOME TO ROAD READY-A CAR RENTAL PLATFORM-------");
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        try{
            User user = authDao.login(username,password);
            switch(user.getRole().toString()){
                case "CUSTOMER":
                    System.out.println("Welcome "+username);
                    while(true){
                        System.out.println("1.Make Reservations");
                        System.out.println("2.View Cars");
                        System.out.println("3.Cancel Reservation");
                        System.out.println("4.Change Reservation");
                        System.out.println("0.Exit");

                        int op = sc.nextInt();
                        if(op == 0)
                            break;
                        switch(op){
                            case 1:
                                sc.nextLine();
                                System.out.println("RESERVE OUR BEST SUITED CAR FOR YOUR BEST JOURNEY");
                                System.out.println("Enter the Location: ");
                                String loc = sc.nextLine();
                                System.out.println("Enter the Pickup Date: (yyyy-mm-dd)");
                                String p_date = sc.nextLine();
                                //reservation.setP_date(LocalDate.parse(p_date));
                                System.out.println("Enter the Drop Date: (yyyy-mm-dd)");
                                String d_date = sc.nextLine();
                                //reservation.setD_date(LocalDate.parse(d_date));
                                System.out.println("Enter the Car id: ");
                                int car_id = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Reservation_status: ");
                                String res_status = sc.nextLine();
                                //reservationService.add(reservation,username);
                                reservationDao.save(new Reservation(loc, LocalDate.parse(p_date),LocalDate.parse(d_date),car_id,Reservation_Status.valueOf(res_status)),username,car_id);
                                break;

                            case 2:
                                System.out.println("--------View All Cars------");
                                List<Car>list = new ArrayList<>();
                                list = carDao.findAll();
                                list.forEach(System.out::println);
                                break;

                            case 3:
                                System.out.println("-------CANCELING THE REGISTRATION--------");
                                System.out.println("Enter the reservation id to be deleted: ");
                                int d_id = sc.nextInt();
                                int i = 0;
                                try {
                                    reservationDao.getById(d_id, username);
                                }
                                catch(NoResultException e){
                                    i=1;
                                    System.out.println(e.getMessage());
                                }
                                if(i==0) {
                                    reservationDao.delete(d_id);
                                    System.out.println("RESERVATION CANCELLED SUCCESSFULLY");
                                }
                                break;

                            case 4:
                                System.out.println("---------CHANGE THE REGISTRATION--------");
                                System.out.println("Enter the id to update: ");
                                int id = sc.nextInt();
                                try{
                                   reservationDao.getById(id,username);
                                   System.out.println("Existing reservation  record");
                                   System.out.println("Enter values for update..");
                                   sc.nextLine();
                                    System.out.println("Enter the Location: ");
                                    String n_loc = sc.nextLine();
                                    System.out.println("Enter the Pickup Date: (yyyy-mm-dd)");
                                    String n_p_date = sc.nextLine();
                                    //reservation.setP_date(LocalDate.parse(p_date));
                                    System.out.println("Enter the Drop Date: (yyyy-mm-dd)");
                                    String n_d_date = sc.nextLine();
                                    //reservation.setD_date(LocalDate.parse(d_date));
                                    System.out.println("Enter the Car id: ");
                                    int n_car_id = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("Reservation_status: ");
                                    String n_res_status = sc.nextLine();
                                    //reservationService.add(reservation,username);
                                    reservationDao.save(new Reservation(n_loc, LocalDate.parse(n_p_date),LocalDate.parse(n_d_date),n_car_id,Reservation_Status.valueOf(n_res_status)),username,n_car_id);

                                }
                                catch(ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;





                        }
                    }
                    break;
                default:
                    System.out.println("Invalid role");
            }
        }
        catch(NoResultException e){
            System.out.println("Invalid Credentials");
        }
        context.close();
    }
}
