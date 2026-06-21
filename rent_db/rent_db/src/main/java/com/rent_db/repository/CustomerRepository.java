package com.rent_db.repository;
import com.rent_db.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("""
            select c from Customer c where c.name=:name
            """)
    Customer findByName(@Param("name") String name);


    @Query("""
          select c from Customer c where c.dl_no=:dl_no
""")
    Customer findByDlNo(@Param("dl_no") int dlNo);

    Customer findByUserUsername(String customerUsername);


    @Query("""
select c from Customer c where c.user.username=?1
""")
    Page<Customer> getCustomerByAdmin(String adminUsername, Pageable pageable);

    @Query("""
select c from Customer c where c.isActive = ?1
""")

    Page<Customer> getAllActive(boolean b, Pageable pageable);

}
