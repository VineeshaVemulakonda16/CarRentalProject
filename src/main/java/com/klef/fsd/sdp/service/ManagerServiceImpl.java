package com.klef.fsd.sdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsd.sdp.model.Car;
import com.klef.fsd.sdp.model.Manager;
import com.klef.fsd.sdp.model.BookCar;
import com.klef.fsd.sdp.repository.CarRepository;
import com.klef.fsd.sdp.repository.ManagerRepository;
import com.klef.fsd.sdp.repository.BookCarRepository;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService
{
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private CarRepository carRepository;
    
    @Autowired
    private BookCarRepository bookCarRepository;

    @Override
    public Manager checkmanagerlogin(String username, String password)
    {
        return managerRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public String addCar(Car car)
    {
        carRepository.save(car);
        return "Car Added Successfully";
    }

    @Override
    public Manager getManagerById(int mid)
    {
        return managerRepository.findById(mid).get();
    }

    @Override
    public List<Car> viewCarsByManager(int mid)
    {
        Manager manager = managerRepository.findById(mid).orElse(null);
        return carRepository.findByManager(manager);
    }

    @Override
    public List<BookCar> getBookingsByManager(int mid)
    {
        return bookCarRepository.getBookingsByManager(mid);
    }

    @Override
    public String updateBookingStatus(int id, String status)
    {
        bookCarRepository.updateStatusById(status, id);
        return "Booking Status Updated Successfully";
    }

    @Override
    public String managerUpdateProfile(Manager manager)
    {
        managerRepository.save(manager);  // Save acts as insert or update
        return "Manager Profile Updated Successfully";
    }
}