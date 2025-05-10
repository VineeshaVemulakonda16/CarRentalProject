package com.klef.fsd.sdp.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "bookcar_table")
public class BookCar 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private String startdate;

    @Column(nullable = false)
    private String enddate;

    @Column(nullable = false)
    private int bookedunits;

    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime bookingtime;
    
    @Column(name = "pickup_location", length = 255)
    private String pickupLocation;

    @Column(name = "delivery_option", length = 50)
    private String deliveryOption;
    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getBookedunits() {
        return bookedunits;
    }

    public void setBookedunits(int bookedunits) {
        this.bookedunits = bookedunits;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingtime() {
        return bookingtime;
    }

    public void setBookingtime(LocalDateTime bookingtime) {
        this.bookingtime = bookingtime;
    }
    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    @Override
    public String toString() {
        return "BookCar [id=" + id + ", car=" + car + ", customer=" + customer + ", startdate=" + startdate
                + ", enddate=" + enddate + ", bookedunits=" + bookedunits + ", status=" + status
                + ", bookingtime=" + bookingtime + ", pickupLocation=" + pickupLocation + ", deliveryOption="
                + deliveryOption + "]";
    }
}