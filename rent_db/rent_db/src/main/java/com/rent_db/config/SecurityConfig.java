package com.rent_db.config;

import com.rent_db.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.apache.tomcat.util.http.Method.PUT;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
//    @Bean
//    public UserDetailsService users() {
//        UserDetails customer = User.builder()
//                .username("john_doe")
//                .password("{noop}cust123")
//                .roles("CUSTOMER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin_01")
//                .password("{noop}admin123")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(customer, admin);
//    }
    private final UserService userService;
    private JwtFilter jwtFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    //.csrf(Customizer.withDefaults())
                    .csrf(AbstractHttpConfigurer::disable)  //the Spring will now allow post,put,delete
                    //.httpBasic(Customizer.withDefaults())    //to not get 403 Forbidden error
                    //.formLogin(Customizer.withDefaults())
                    .authorizeHttpRequests(authorize -> authorize
                                    //.requestMatchers(HttpMethod.POST,"/api/")

                                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            //1. REGISTRATION - BOTH ADMIN AND CUSTOMER CAN REGISTER
                                    .requestMatchers(HttpMethod.POST,"/api/agent/add").hasAnyAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.POST,"/api/admin/add").permitAll()
                                    .requestMatchers(HttpMethod.POST,"/api/customer/add").permitAll()


                            //2. LOGIN
                                    .requestMatchers(HttpMethod.GET,"/api/auth/login").permitAll()


                            //3.CHECK ROLE
                                    .requestMatchers(HttpMethod.PUT,"/api/user/update/{id}").authenticated()
                                    .requestMatchers(HttpMethod.GET,"/api/user/get-one/{id}").authenticated()
                                    .requestMatchers(HttpMethod.GET,"/api/auth/user-details").authenticated()
                                    .requestMatchers(HttpMethod.GET,"/api/user/getByRole").authenticated()


                            //3.1 VIEW AND BROWSE CARS
                                    .requestMatchers(HttpMethod.GET,"/api/car/all").authenticated()
                                    .requestMatchers(HttpMethod.GET,"/api/car/paginated/car").permitAll()
                                    .requestMatchers(HttpMethod.GET,"/api/car/get-one/{id}").authenticated()
                                    .requestMatchers(HttpMethod.GET,"/api/car/availability").authenticated()
                                    .requestMatchers(HttpMethod.GET,"/api/customer/getByName").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.GET,"/api/customer/getBydl_No").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.GET,"/api/reservation/getByStatus").permitAll()
                                    .requestMatchers(HttpMethod.POST,"/api/reservation/approveReservation/{id}").permitAll()

                                    .requestMatchers(HttpMethod.GET,"/api/car/search").permitAll()





                            //3.2 ADD RESERVATION
                                    .requestMatchers(HttpMethod.POST,"/api/reservation/add").hasAuthority("CUSTOMER")
                                    .requestMatchers(HttpMethod.GET,"/api/car/byReservationId/{id}").hasAuthority("CUSTOMER")


                            //3.3 RENTAL_AGENT
                                    .requestMatchers(HttpMethod.GET,"/api/agent/all").permitAll()
                                    .requestMatchers(HttpMethod.GET,"/api/agent/paginated/all").permitAll()
                                    .requestMatchers(HttpMethod.POST,"/api/agent/add").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"/api/agent/update").hasAnyAuthority("ADMIN","RENTAL_AGENT")
                                    .requestMatchers(HttpMethod.DELETE,"/api/agent/delete").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/api/agent/soft-delete/{id}").permitAll()


                            //4. PAYMENT
                                    .requestMatchers(HttpMethod.POST,"/api/payment/pay").hasAuthority("CUSTOMER")
                                    .requestMatchers(HttpMethod.PUT,"/api/payment/approve/{id}").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"/api/payment/reject/{id}").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.GET,"/api/payment/all").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.GET,"/api/payment/get-one/{id}").hasAuthority("ADMIN")


                            //5. ADMIN

                            //5.1 MANAGE USERS
                                    .requestMatchers(HttpMethod.GET,"/api/admin/all").authenticated()
                                    .requestMatchers(HttpMethod.GET,"/api/customer/all").permitAll()

                                    .requestMatchers(HttpMethod.GET,"/api/admin/paginated/all").authenticated()

                                    .requestMatchers(HttpMethod.GET,"/api/customer/get-one").hasAnyAuthority("ADMIN","CUSTOMER")
                                    .requestMatchers(HttpMethod.GET,"/api/admin/get-one").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.GET,"/api/user/get-one/{id}").hasAuthority("ADMIN")

                                    .requestMatchers(HttpMethod.PUT,"/api/admin/update").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"api/customer/update").permitAll()
                                    .requestMatchers(HttpMethod.PUT,"api/customer/change-password").permitAll()
                                    .requestMatchers(HttpMethod.PUT,"/api/user/update/{id}").hasAuthority("ADMIN")

                                    .requestMatchers(HttpMethod.DELETE,"/api/user/delete/{id}").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/customer/delete").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/admin/delete").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/car/delete/{id}").hasAuthority("ADMIN")

                                    .requestMatchers(HttpMethod.GET,"/api/customer/all/for-admin").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/api/customer/soft-delete/{id}").permitAll()


                            //5.2 MANAGE VEHICLES
                                    .requestMatchers(HttpMethod.POST,"/api/car/add").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/car/delete/{id}").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"/api/car/update/{id}").permitAll()
                                    .requestMatchers(HttpMethod.DELETE, "/api/car/soft-delete/{id}").permitAll()


                            //5.3 MANAGE RESERVATIONS
                                    .requestMatchers(HttpMethod.GET,"/api/reservation/all").authenticated()
                                    .requestMatchers(HttpMethod.GET,"/api/reservation/get-id/{id}").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"/api/reservation/update/{id}").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/reservation/delete/{id}").hasAnyAuthority("ADMIN","CUSTOMER")


                                    .requestMatchers(HttpMethod.GET,"/api/reservation/all/for-customer").hasAuthority("CUSTOMER")
                                    .requestMatchers(HttpMethod.GET,"/api/reservation/all/for-agent").hasAuthority("RENTAL_AGENT")


                                    .requestMatchers(HttpMethod.POST,"/api/reservation/assign/{reservationId}").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.GET,"/api/reservation/getOverallAmount/{id}").permitAll()
                                    .requestMatchers(HttpMethod.POST,"/api/reservation/accepts_reservation").hasAuthority("RENTAL_AGENT")
                                    .requestMatchers(HttpMethod.PUT,"/api/reservation/return/{id}").permitAll()



                            //UPLOADING FILES
                            //1. admin can add car image
                                    .requestMatchers(HttpMethod.POST,"/api/car/image/upload").permitAll()

                            //2. customer can attach their driving license for verification
                                    .requestMatchers(HttpMethod.POST,"/api/customer/driving_license/upload").permitAll()

                            //6. REVIEW
                                    .requestMatchers(HttpMethod.GET,"/api/review/all").hasAnyAuthority("CUSTOMER","ADMIN","RENTAL_AGENT")
                                    .requestMatchers(HttpMethod.GET,"/api/review/get-one/{id}").hasAuthority("CUSTOMER")
                                    .requestMatchers(HttpMethod.POST,"/api/review/add").permitAll()
                                    .requestMatchers(HttpMethod.PUT,"/api/review/update/{id}").hasAnyAuthority("CUSTOMER", "ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/review/delete/{id}").hasAnyAuthority("CUSTOMER", "ADMIN")
                                    .requestMatchers(HttpMethod.GET,"/api/review/getByCarId/{id}").hasAuthority("CUSTOMER")
                                    .requestMatchers(HttpMethod.GET,"api/review/getByCustomer").hasAuthority("CUSTOMER")
                                    .requestMatchers(HttpMethod.GET,"/api/review/getByRating").hasAuthority("CUSTOMER")


                            //DashBoard
                                    .requestMatchers(HttpMethod.GET,"/api/dashboard/stats").permitAll()




                            .anyRequest().authenticated()
                            //.anyRequest().permitAll()   //it will permit all without username and password
                    );
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            http.httpBasic(Customizer.withDefaults());

            return http.build();
        }

    }


