package com.klef.fsd.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsd.sdp.model.Admin;
import com.klef.fsd.sdp.model.Customer;
import com.klef.fsd.sdp.model.Manager;
import com.klef.fsd.sdp.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController 
{
  @Autowired
  private AdminService adminService;
  
  @PostMapping("/checkadminlogin")
  public ResponseEntity<?> checkadminlogin(@RequestBody Admin admin)
  {
	  try 
      {
          Admin a = adminService.checkadminlogin(admin.getUsername(), admin.getPassword());

          if (a!=null) 
          {
              return ResponseEntity.ok(a); 
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
  
  @GetMapping("/viewallcustomers")
  public ResponseEntity<List<Customer>> viewallcustomers()
  {
	 List<Customer> customers =  adminService.displaycustomers();
	 
	 return ResponseEntity.ok(customers); 
  }
  
  @PostMapping("/addcarmanager")
  public ResponseEntity<String> addmanager(@RequestBody Manager manager)
  {
	   try
	   {
		  String output = adminService.addmanager(manager);
		  return ResponseEntity.ok(output); 
	   }
	   catch(Exception e)
	   {
		   return ResponseEntity.status(500).body("Failed to Add Car Manager ... !!"); 
	   }
  }
  
  @GetMapping("/viewallcarmanagers")
  public ResponseEntity<List<Manager>> viewallcarmanagers()
  {
	 List<Manager> carmanagers =  adminService.displaymanagers();
	 
	 return ResponseEntity.ok(carmanagers); 
  }
  
  @DeleteMapping("/deletecustomer")
  public ResponseEntity<String> deletecustomer(@RequestParam int cid)
  {
	  try
	   {
		  String output = adminService.deletecustomer(cid);
		  return ResponseEntity.ok(output);
	   }
	   catch(Exception e)
	   {
		    return ResponseEntity.status(500).body("Failed to Delete Customer ... !!"); 
	   }
  }
  
  @DeleteMapping("/deletemanager/{mid}")
  public ResponseEntity<String> deletemanager(@PathVariable int mid)
  {
	  try
	   {
		  String output = adminService.deletemanager(mid);
		  return ResponseEntity.ok(output);
	   }
	   catch(Exception e)
	   {
		    return ResponseEntity.status(500).body("Failed to Delete Manager ... !!"); 
	   }
  
  }
  
  @GetMapping("/customercount")
  public ResponseEntity<Long> getCustomerCount()
  {
      long count = adminService.displaycustomercount();
      return ResponseEntity.ok(count);
  }

  @GetMapping("/managercount")
  public ResponseEntity<Long> getManagerCount()
  {
      long count = adminService.displaymanagercount();
      return ResponseEntity.ok(count);
  }
}