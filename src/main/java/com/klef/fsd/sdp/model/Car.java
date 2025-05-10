package com.klef.fsd.sdp.model;

import java.sql.Blob;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "car_table")
public class Car 
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "car_id")
  private int id;

  @Column(name = "car_category", nullable = false, length = 100)
  private String category;

  @Column(name = "car_name", nullable = false, length = 100)
  private String name;

  @Column(name = "car_desc", nullable = false, length = 500)
  private String description;

  @Column(name = "car_cost", nullable = false)
  private double cost;

  @Column(name = "car_type", nullable = false, length = 200)
  private String type;

  @Column(name = "car_image", nullable = false)
  private Blob image;

  private String imageBase64; // ✅ add this

  // All your existing getters and setters...

  public String getImageBase64() {
      return imageBase64;
  }

  public void setImageBase64(String imageBase64) {
      this.imageBase64 = imageBase64;
  }
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "manager_id") // Foreign key column
  private Manager manager;
  
  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Blob getImage() {
    return image;
  }

  public void setImage(Blob image) {
    this.image = image;
  }

public Manager getManager() {
	return manager;
}

public void setManager(Manager manager) {
	this.manager = manager;
}
}