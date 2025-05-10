package com.klef.fsd.sdp.service;

import java.util.List;

import com.klef.fsd.sdp.model.Car;
import com.klef.fsd.sdp.model.Manager;
import com.klef.fsd.sdp.model.BookCar;

public interface ManagerService 
{
    // Check manager login credentials
    public Manager checkmanagerlogin(String username, String password);
    
    // Add a new car to the system
    public String addCar(Car car);
    
    // View all cars managed by the manager
    public List<Car> viewCarsByManager(int mid);
    
    // Get manager details by ID
    public Manager getManagerById(int mid);
    
    // Get bookings for cars managed by the manager
    public List<BookCar> getBookingsByManager(int mid);
    
    // Update booking status (e.g., confirmed, canceled)
    public String updateBookingStatus(int id, String status);
    
    // Update manager profile
    public String managerUpdateProfile(Manager manager);
}