package com.hotel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDAO 
{
    public boolean createBooking(Booking booking) throws SQLException
    {
       String sql="INSERT INTO booking"+"(customer_name,email,room_id,check_in,check_out,total_amount,booking_status)"
                                       +"VALUES(?,?,?,?,?,?,?)";
       try(Connection con=DBConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(sql))
       {
         
          stmt.setString(1,booking.getCustomerName());
          stmt.setString(2,booking.getEmail());
          stmt.setInt(3,booking.getRoomId());
          stmt.setDate(4,booking.getCheckIn());
          stmt.setDate(5,booking.getCheckOut());
          stmt.setDouble(6,booking.getTotalAmount());
          stmt.setString(7,booking.getBookingStatus());
          int rowsInserted=stmt.executeUpdate();
         return rowsInserted > 0; 
       }
    }
    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut) throws SQLException
    {
      String sql="SELECT COUNT(*) FROM  booking "+
                  "WHERE room_Id= ? "+
                  "AND booking_status = 'CONFIRMED' "+
                  "AND check_In < ? "+
                  "AND check_Out > ? ";
      try(Connection con=DBConnection.getConnection();
         PreparedStatement stmt=con.prepareStatement(sql))
      {
         
         stmt.setInt(1,roomId);
         stmt.setDate(2,checkOut);
         stmt.setDate(3,checkIn);
         try(ResultSet res=stmt.executeQuery())
         {
            res.next();
            return res.getInt(1)==0;
         }

      }
      
    }
    public Booking getBookingById(int bookingId) throws SQLException
    {
      String sql="SELECT *FROM booking WHERE booking_id = ? ";
      try(Connection con=DBConnection.getConnection();
         PreparedStatement stmt=con.prepareStatement(sql))
      {
         
         stmt.setInt(1,bookingId);
         try(ResultSet res=stmt.executeQuery())
         {
           if(res.next())
           {
            return new Booking(
                   res.getInt("booking_id"),
                  res.getString("customer_name"),
                  res.getString("email"),
                  res.getInt("room_id"),
                  res.getDate("check_in"),
                  res.getDate("check_out"),
                  res.getDouble("total_amount"),
                  res.getString("booking_status") );
           }
         }
      }
      return null;
    }
   public boolean cancelBooking(int bookingId) throws SQLException
   {
      String sql="UPDATE booking "+
                 "SET booking_status = 'CANCELLED' "+
                 "WHERE booking_id = ? "+
                 "AND booking_status = 'CONFIRMED' "+
                 "AND payment_status = 'UNPAID' ";
      try(Connection con=DBConnection.getConnection();
          PreparedStatement stmt=con.prepareStatement(sql))
      {
        
        stmt.setInt(1,bookingId);
        int rowsUpdated=stmt.executeUpdate();
        System.out.println("Rows updated: " + rowsUpdated);
        return rowsUpdated > 0;
      }
   }
   public boolean processPayment(int bookingId) throws SQLException
   {
      String sql="UPDATE booking "+
                 "SET payment_status = 'PAID' "+
                 "WHERE booking_id = ? "+
                 "AND booking_status = 'CONFIRMED' "+
                 "AND payment_status = 'UNPAID'";
      try(Connection con=DBConnection.getConnection();
         PreparedStatement stmt=con.prepareStatement(sql))
      {
         
         stmt.setInt(1,bookingId);
         int rowsUpdated=stmt.executeUpdate();
         return rowsUpdated > 0;
      }
      
   }
}
