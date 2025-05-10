package com.klef.fsd.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.fsd.sdp.model.Car;
import com.klef.fsd.sdp.model.Manager;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>
{
    public List<Car> findByCategory(String category);

    public List<Car> findByManager(Manager manager); // assuming Car has a Manager field

    @Query("select count(c) from Car c")
    public long carCount();
}