package com.hotel;

import java.sql.Date;

public class Booking
 {
    private int bookingId;
    private String customerName;
    private String email;
    private int roomId;
    private Date checkIn;
    private Date checkOut;
    private double totalAmount;
    private String bookingStatus;

    public Booking(int bookingId,String customerName,String email,int roomId,Date checkIn,Date checkOut,double totalAmount,String bookingStatus)
    {
        this.bookingId=bookingId;
        this.customerName=customerName;
        this.email=email;
        this.roomId=roomId;
        this.checkIn=checkIn;
        this.checkOut=checkOut;
        this.totalAmount=totalAmount;
        this.bookingStatus=bookingStatus;

    }
    public int getBookingId()
    {
        return bookingId;
    }
    public String getCustomerName()
    {
        return customerName;
    }
    public String getEmail()
    {
        return email;
    }
    public int getRoomId()
    {
        return roomId;
    }
    public Date getCheckIn()
    {
        return checkIn;
    }
    public Date getCheckOut()
    {
        return checkOut;
    }
    public double getTotalAmount()
    {
        return totalAmount;
    }
    public String getBookingStatus()
    {
        return bookingStatus;
    }    
         
  
}
