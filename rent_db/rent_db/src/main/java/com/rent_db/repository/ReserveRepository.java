package com.rent_db.repository;

import com.rent_db.dto.ReserveRespDto;
import com.rent_db.enums.Reservation_Status;
import com.rent_db.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.net.ContentHandler;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reservation,Integer> {

    @Query("""
            select r from Reservation r where r.reservation_status=:reservation_status and r.rentalAgent.id =:agentId
            """)
    Page<Reservation> getByStatus(@Param("reservation_status")Reservation_Status reservationStatus,@Param("agentId") int agentId,Pageable pageable);

    @Query("""
select r from Reservation r where r.customer.user.username=?1
""")

//    Page<Reservation> getReservationByCustomer(String customerUsername, Pageable pageable);
    List<Reservation> getReservationByCustomer(String customerUsername);


    @Query("""
select r from Reservation r where r.rentalAgent.user.username=?1
""")

    Page<Reservation> getReservationByAgent(String username,Pageable pageable);

    @Query("""
select r from Reservation r where r.rentalAgent.user.username=?1
""")
    Reservation getReservationByAgent(String username);
}
