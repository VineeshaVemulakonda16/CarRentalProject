package com.klef.fsd.sdp.service;

import java.util.List;

import com.klef.fsd.sdp.model.Admin;
import com.klef.fsd.sdp.model.Customer;
import com.klef.fsd.sdp.model.Manager;

public interface AdminService 
{
  public Admin checkadminlogin(String username,String password);
  
  public String addmanager(Manager manager);
  public List<Manager> displaymanagers();

  public List<Customer> displaycustomers();
  public String deletecustomer(int cid);
  public String deletemanager(int mid);
  
  public long displaycustomercount();
  public long displaymanagercount();

}