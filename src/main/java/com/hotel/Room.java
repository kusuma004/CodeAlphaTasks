package com.hotel;

public class Room
 { 
      //room details
     private int roomId;
     private String roomNo;
     private String roomType;
     private double pricePerNight;
     private int capacity;
     private String status;

     //construtor
     public Room(int roomId,String roomNo,String roomType,double pricePerNight,int capacity,String status)
     {
        this.roomId=roomId;
        this.roomNo=roomNo;
        this.roomType=roomType;
        this.pricePerNight=pricePerNight;
        this.capacity=capacity;
        this.status=status;
     }
     //getters
     public int getRoomId()
     {
        return roomId;
     }
     public String getRoomNo()
     {
        return roomNo;
     }
     public String getRoomType()
     {
        return roomType;
     }
     public double getPricePerNight()
     {
        return pricePerNight;
     }
     public int getCapacity()
     {
        return capacity;
     }
     public String getStatus()
     {
        return status;
     }
     @Override
public String toString() 
  {
    return "Room ID: " + roomId +
           ", Room No: " + roomNo +
           ", Room Type: " + roomType +
           ", Price Per Night: " + pricePerNight +
           ", Capacity: " + capacity +
           ", Status: " + status;
  }
}
