package com.klef.fsd.sdp.controller;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.klef.fsd.sdp.dto.CarDTO;
import com.klef.fsd.sdp.model.Car;
import com.klef.fsd.sdp.service.CarService;

@RestController
@RequestMapping("/car")
@CrossOrigin("*")
public class CarController 
{
  @Autowired
  private CarService carService;

  // to add or insert the car with image
  @PostMapping("/addcar")
  public ResponseEntity<String> addCar(
         @RequestParam String category,
         @RequestParam String name,
         @RequestParam String description,
         @RequestParam double cost,
         @RequestParam String type,
         @RequestParam("carimage") MultipartFile file) {
     try {
         byte[] bytes = file.getBytes();
         Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

         Car c = new Car();
         c.setCategory(category);
         c.setName(name);
         c.setDescription(description);
         c.setCost(cost);
         c.setType(type);
         c.setImage(blob);

         String output = carService.addCar(c);
         return ResponseEntity.ok(output);

     } catch (Exception e) {
         return ResponseEntity.status(500).body("Error: " + e.getMessage());
     }
 }

  // to view or display all cars
  @GetMapping("viewallcars")
  public ResponseEntity<List<CarDTO>> viewAllCars() 
  {
     List<Car> carList = carService.viewAllCars();
     List<CarDTO> carDTOList = new ArrayList<>();

     for (Car c : carList) {
         CarDTO dto = new CarDTO();
         dto.setId(c.getId()); // ✅ ADD THIS LINE
         dto.setCategory(c.getCategory());
         dto.setName(c.getName());
         dto.setDescription(c.getDescription());
         dto.setCost(c.getCost());
         dto.setType(c.getType());
         carDTOList.add(dto);
     }

     return ResponseEntity.ok(carDTOList);
  }

  // to display car image by id
  @GetMapping("displaycarimage")
  public ResponseEntity<byte[]> displayCarImage(@RequestParam int id) throws Exception
  {
    Car car =  carService.viewCarById(id);
    byte [] imageBytes = car.getImage().getBytes(1, (int) car.getImage().length());

    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
  }

  // to view or display car by id
  @PostMapping("displaycarbyid")
  public ResponseEntity<CarDTO> displayCarDemo(@RequestParam int cid)
  {
     Car c = carService.viewCarById(cid);
     CarDTO dto = new CarDTO();
     dto.setId(c.getId()); // ✅ ADD THIS LINE
     dto.setCategory(c.getCategory());
     dto.setName(c.getName());
     dto.setDescription(c.getDescription());
     dto.setCost(c.getCost());
     dto.setType(c.getType());
     return ResponseEntity.ok(dto);
  }

  // to get car by id via path variable
  @GetMapping("/getcarbyid/{cid}")
  public ResponseEntity<CarDTO> getCarById(@PathVariable int cid)
  {
     Car c = carService.viewCarById(cid);

     if (c == null) {
         return ResponseEntity.notFound().build();
     }

     CarDTO dto = new CarDTO();
     dto.setId(c.getId()); // ✅ ADD THIS LINE
     dto.setCategory(c.getCategory());
     dto.setName(c.getName());
     dto.setDescription(c.getDescription());
     dto.setCost(c.getCost());
     dto.setType(c.getType());

     return ResponseEntity.ok(dto);
  }
  
  // to delete car by id
  @DeleteMapping("/deletecar/{id}")
  public ResponseEntity<String> deleteCar(@PathVariable int id) {
      try {
          String result = carService.deleteCar(id);
          return ResponseEntity.ok(result);
      } catch (Exception e) {
          return ResponseEntity.status(500).body("Error deleting car: " + e.getMessage());
      }
  }

  // to update car details
  @PutMapping("/updatecar")
  public ResponseEntity<String> updateCar(@RequestBody CarDTO carDTO) {
      try {
          Car updatedCar = new Car();
          updatedCar.setId(carDTO.getId());
          updatedCar.setName(carDTO.getName());
          updatedCar.setCategory(carDTO.getCategory());
          updatedCar.setDescription(carDTO.getDescription());
          updatedCar.setCost(carDTO.getCost());
          updatedCar.setType(carDTO.getType());

          String result = carService.updateCar(updatedCar);
          return ResponseEntity.ok(result);
      } catch (Exception e) {
          return ResponseEntity.status(500).body("Update failed: " + e.getMessage());
      }
  }
}