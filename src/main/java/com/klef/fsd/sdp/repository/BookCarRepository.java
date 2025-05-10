package com.klef.fsd.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.klef.fsd.sdp.model.BookCar;
import com.klef.fsd.sdp.model.Customer;

@Repository
public interface BookCarRepository extends JpaRepository<BookCar, Integer>
{
    public List<BookCar> findByCustomer(Customer customer);
  
    @Query("SELECT b FROM BookCar b JOIN FETCH b.car c WHERE c.manager.id = ?1")
    List<BookCar> getBookingsByManager(int mid);
    
    @Modifying
    @Transactional
    @Query("UPDATE BookCar b SET b.status = ?1 WHERE b.id = ?2")
    int updateStatusById(String status, int id);
}