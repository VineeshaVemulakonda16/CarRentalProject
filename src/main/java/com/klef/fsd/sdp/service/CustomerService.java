package com.klef.fsd.sdp.service;

import java.util.List;

import com.klef.fsd.sdp.model.BookCar;
import com.klef.fsd.sdp.model.Car;
import com.klef.fsd.sdp.model.Customer;

public interface CustomerService 
{
  public String customerregistration(Customer customer);
  public Customer customerlogin(String username, String password);
  
  public String customerupdateprofile(Customer customer);

  public List<Car> viewAllCars();
  
  public Customer getCustomerById(int cid);
  public Car getCarById(int carId);
  
  public String bookCar(BookCar bookCar);
  public List<BookCar> getBookedCarsByCustomer(int cid);
}