package com.project.config;


import com.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

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
                        // 1. REGISTER
                           //1.1 EMPLOYER
                                .requestMatchers(HttpMethod.POST,"/api/auth/register/employer").permitAll()

                           //1.2 JOB SEEKER
                                .requestMatchers(HttpMethod.POST,"/api/auth/register/seeker").permitAll()



                        // 2. LOGIN
                                .requestMatchers(HttpMethod.GET,"/api/auth/login").authenticated()



                        // 3.EMPLOYER POSTS NEW JOBS
                                .requestMatchers(HttpMethod.POST,"/api/jobs").hasAuthority("EMPLOYER")


                        // 4.BROWSE ALL JOBS
                                .requestMatchers(HttpMethod.GET,"/api/jobs/all").authenticated()


                        // 5. JOBSEEKER APPLIES FOR A JOB
                                .requestMatchers(HttpMethod.POST,"/api/applications").hasAuthority("JOBSEEKER")


                        // 6. JOBSEEKER VIEW THEIR OWN APPLICATION
                                .requestMatchers(HttpMethod.GET,"/api/my-applications").hasAuthority("JOBSEEKER")


                                .anyRequest().authenticated()
                        //.anyRequest().permitAll()   //it will permit all without username and password
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
