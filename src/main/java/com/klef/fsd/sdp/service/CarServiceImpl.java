package com.klef.fsd.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsd.sdp.model.Car;
import com.klef.fsd.sdp.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService
{
  @Autowired
  private CarRepository carRepository;

  @Override
  public String addCar(Car car) 
  {
     carRepository.save(car);
     return "Car Added Successfully";
  }

  @Override
  public List<Car> viewAllCars() 
  {
      return carRepository.findAll();
  }

  @Override
  public Car viewCarById(int cid) 
  {
     return carRepository.findById(cid).orElse(null);
  }

  @Override
  public List<Car> viewCarsByCategory(String category) 
  {
    return carRepository.findByCategory(category);
  }
  @Override
  public String deleteCar(int cid) {
      carRepository.deleteById(cid);
      return "Car deleted successfully!";
  }
  
  @Override
  public String updateCar(Car updatedCar) {
      Car existingCar = carRepository.findById(updatedCar.getId()).orElse(null);
      if (existingCar == null) {
          return "Car not found!";
      }

      existingCar.setName(updatedCar.getName());
      existingCar.setCategory(updatedCar.getCategory());
      existingCar.setDescription(updatedCar.getDescription());
      existingCar.setCost(updatedCar.getCost());
      existingCar.setType(updatedCar.getType());

      carRepository.save(existingCar);
      return "Car details updated successfully!";
  }

}