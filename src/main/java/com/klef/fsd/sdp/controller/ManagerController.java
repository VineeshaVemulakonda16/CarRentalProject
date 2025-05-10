package com.klef.fsd.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsd.sdp.dto.CarDTO;
import com.klef.fsd.sdp.model.BookCar;
import com.klef.fsd.sdp.model.Car;
import com.klef.fsd.sdp.model.Manager;
import com.klef.fsd.sdp.service.CarService;
import com.klef.fsd.sdp.service.ManagerService;

@RestController
@RequestMapping("/manager")
@CrossOrigin("*")
public class ManagerController 
{
   @Autowired
   private ManagerService managerService;
   
   @Autowired
   private CarService CarService;
	   
   @PostMapping("/checkmanagerlogin")
   public ResponseEntity<?> checkmanagerlogin(@RequestBody Manager manager) 
   {
       try 
       {
           Manager m = managerService.checkmanagerlogin(manager.getUsername(), manager.getPassword());

           if (m!=null) 
           {
               return ResponseEntity.ok(m); 
           } 
           else 
           {
               return ResponseEntity.status(401).body("Invalid Username or Password"); 
           }
       } 
       catch (Exception e) 
       {
           return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
       }
   }
   @PutMapping("/updateprofile")
   public ResponseEntity<String> managerUpdateProfile(@RequestBody Manager manager)
   {
       try
       {
           System.out.println(manager.toString());
           String output = managerService.managerUpdateProfile(manager);
           return ResponseEntity.ok(output);
       }
       catch(Exception e)
       {
           System.out.println(e.getMessage());
           return ResponseEntity.status(500).body("Failed to Update Manager Profile ... !!");
       }
   }
   
   @PostMapping("/addcar")
   public ResponseEntity<String> addCar(@RequestBody CarDTO dto) 
   {
       try 
       {
           Manager manager = managerService.getManagerById(dto.getManager_id()); // assuming manager_id is a part of CarDTO

           Car car = new Car();
           car.setCategory(dto.getCategory());
           car.setName(dto.getName());
           car.setDescription(dto.getDescription());
           car.setCost(dto.getCost());
           car.setType(dto.getType());
           car.setManager(manager); // assuming Car is associated with Manager

           String output = CarService.addCar(car);
           return ResponseEntity.ok(output); // 200 - success
       } 
       catch (Exception e) 
       { 
           return ResponseEntity.status(500).body("Failed to Add Car: " + e.getMessage());
       }
   }

   @GetMapping("/viewcarsbymanager/{id}")
   public ResponseEntity<List<Car>> viewCarsByManager(@PathVariable int id) 
   {
       List<Car> cars = managerService.viewCarsByManager(id); // Assuming carService has this method
       return ResponseEntity.ok(cars);
   }

   @GetMapping("/viewbookingsbymanager/{managerId}")
   public ResponseEntity<List<BookCar>> viewBookingsByManager(@PathVariable int managerId) {
       try {
           List<BookCar> bookings = managerService.getBookingsByManager(managerId);
           return ResponseEntity.ok(bookings);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(500).body(null);
       }
   }



   @GetMapping("/updatebookingstatus")
   public ResponseEntity<String> updateBookingStatus(@RequestParam int id, @RequestParam String status) 
   { 
       try
       {
           String output = managerService.updateBookingStatus(id, status); // Assuming carService handles bookings and status updates
           return ResponseEntity.ok(output);
       }
       catch (Exception e) 
       {
           System.out.println(e.getMessage());
           return ResponseEntity.status(500).body("Error:" + e.getMessage());
       }
   }
}