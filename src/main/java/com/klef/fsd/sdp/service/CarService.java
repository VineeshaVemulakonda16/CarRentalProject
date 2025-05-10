package com.klef.fsd.sdp.service;

import java.util.List;

import com.klef.fsd.sdp.model.Car;

public interface CarService 
{
   public String addCar(Car car);
   public List<Car> viewAllCars();
   public Car viewCarById(int cid);
   public List<Car> viewCarsByCategory(String category);
   public String deleteCar(int cid);
   public String updateCar(Car car);

}