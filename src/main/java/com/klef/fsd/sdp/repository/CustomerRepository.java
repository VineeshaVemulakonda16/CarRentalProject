package com.klef.fsd.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.fsd.sdp.model.Customer;

import jakarta.transaction.Transactional;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>
{
  public Customer findByUsernameAndPassword(String username, String password);
  
  @Modifying
  @Transactional
  @Query("delete from Customer c where c.location=?1")
  public int deletecustomerbylocation(String location);
  
  @Query("select count(c) from Customer c")
  public long customercount();
}