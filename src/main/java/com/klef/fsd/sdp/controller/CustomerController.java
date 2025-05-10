package com.klef.fsd.sdp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import com.klef.fsd.sdp.dto.BookedCarDTO;
import com.klef.fsd.sdp.dto.CarDTO;
import com.klef.fsd.sdp.model.BookCar;
import com.klef.fsd.sdp.model.Car;
import com.klef.fsd.sdp.model.Customer;
import com.klef.fsd.sdp.service.CustomerService;
import com.klef.fsd.sdp.service.RazorpayService;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*") // * means any URL
public class CustomerController 
{
   @Autowired
   private CustomerService customerService;
   
   @Autowired
   private RazorpayService razorpayService;

   
   @GetMapping("/")
   public String home()
   {
	   return "FSD SDP Project";
   }
   
   @PostMapping("/registration")
   public ResponseEntity<String> customerregistration(@RequestBody Customer customer)
   {
	   try
	   {
		  String output = customerService.customerregistration(customer);
		  return ResponseEntity.ok(output); 
	   }
	   catch(Exception e)
	   {
		   // return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
		   return ResponseEntity.status(500).body("Customer Registration Failed ...");
	   }
   }
   
   @PostMapping("/checkcustomerlogin")
   public ResponseEntity<?> checkcustomerlogin(@RequestBody Customer customer) 
   {
       try 
       {
           Customer c = customerService.customerlogin(customer.getUsername(), customer.getPassword());

           if (c!=null) 
           {
               return ResponseEntity.ok(c); 
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
   public ResponseEntity<String> customerupdateprofile(@RequestBody Customer customer)
   {
 	  try
 	   {
 		  System.out.println(customer.toString());
 		  String output = customerService.customerupdateprofile(customer);
 		  return ResponseEntity.ok(output);
 	   }
 	   catch(Exception e)
 	   {
 		    System.out.println(e.getMessage());
 		    return ResponseEntity.status(500).body("Failed to Update Customer ... !!"); 
 	   }
   }
   @GetMapping("viewallcars")
   public ResponseEntity<List<CarDTO>> viewAllCars() 
   {
       List<Car> carList = customerService.viewAllCars();
       List<CarDTO> carDTOList = new ArrayList<>();

       for (Car c : carList) {
           CarDTO dto = new CarDTO();
           dto.setId(c.getId()); // ✅ Add this line to fix ID issue
           dto.setCategory(c.getCategory());
           dto.setName(c.getName());
           dto.setDescription(c.getDescription());
           dto.setCost(c.getCost());
           dto.setType(c.getType());

           // Convert BLOB to Base64 image (see below)
           if (c.getImage() != null) {
               try {
                   byte[] bytes = c.getImage().getBytes(1, (int) c.getImage().length());
                   String base64Image = java.util.Base64.getEncoder().encodeToString(bytes);
                   dto.setImageBase64(base64Image); // ✅ Set base64 image in DTO
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           carDTOList.add(dto);
       }

       return ResponseEntity.ok(carDTOList);
   }


   @PostMapping("/bookcar")
   public ResponseEntity<String> bookCar(@RequestBody BookCar bookCar) {
       try {
           // Remove manual ID assignment
           // int bookingId = new Random().nextInt(900000) + 100000;
           // bookCar.setId(bookingId);

           // Validate customer and car
           Customer customer = customerService.getCustomerById(bookCar.getCustomer().getId());
           Car car = customerService.getCarById(bookCar.getCar().getId());

           if (customer == null || car == null) {
               throw new Exception("Customer or Car not found");
           }

           bookCar.setCustomer(customer);
           bookCar.setCar(car);
           bookCar.setStatus("BOOKED");
           bookCar.setBookedunits(1);

           String output = customerService.bookCar(bookCar);
           return ResponseEntity.ok(output);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(500).body("Failed to Book a Car: " + e.getMessage());
       }
   }

   @GetMapping("/bookedcars/{cid}")
   public ResponseEntity<List<BookedCarDTO>> getCarsByCustomer(@PathVariable int cid) 
   {
       List<BookCar> bookedCars = customerService.getBookedCarsByCustomer(cid);
       List<BookedCarDTO> bookedCarDTOs = new ArrayList<>();

       for (BookCar bookCar : bookedCars) {
           // Convert Car to CarDTO
           Car car = bookCar.getCar();
           CarDTO carDTO = new CarDTO();
           carDTO.setId(car.getId());
           carDTO.setName(car.getName());
           carDTO.setCategory(car.getCategory());
           carDTO.setDescription(car.getDescription());
           carDTO.setCost(car.getCost());
           carDTO.setType(car.getType());

           // Add the BookedCarDTO
           BookedCarDTO dto = new BookedCarDTO(
               bookCar.getId(),
               carDTO,
               bookCar.getStartdate(),
               bookCar.getEnddate(),
               bookCar.getStatus(),
               bookCar.getBookingtime()
               
           );
           dto.setPickupLocation(bookCar.getPickupLocation()); // Include new fields in DTO
           dto.setDeliveryOption(bookCar.getDeliveryOption());
           bookedCarDTOs.add(dto);
           bookedCarDTOs.add(dto);
       }

       return ResponseEntity.ok(bookedCarDTOs);
   }
   
   
   @PostMapping("/create-order")
   public ResponseEntity<?> createOrder(@RequestParam int amount) {
       try {
           String orderResponse = razorpayService.createOrder(amount);
           if (orderResponse != null) {
               return ResponseEntity.ok(orderResponse);
           } else {
               return ResponseEntity.badRequest().body("Failed to create Razorpay order.");
           }
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(500).body("An error occurred while creating the order.");
       }
   }



}