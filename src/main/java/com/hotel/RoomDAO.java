package com.hotel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoomDAO
{
    public List<Room> getAvailableRooms() throws SQLException
    {
      List<Room> rooms=new ArrayList<>();
      String sql="SELECT *FROM rooms WHERE status='available'";
      try(Connection con=DBConnection.getConnection();
          PreparedStatement stmt=con.prepareStatement(sql);
          ResultSet res=stmt.executeQuery() )
      { 
          while(res.next())
          {
            Room room=new Room(
                res.getInt("roomId"),
                res.getString("roomNo"),
                res.getString("roomType"),
                res.getDouble("pricePerNight"),
                res.getInt("capacity"),
                res.getString("status")
            );
            rooms.add(room);
          }
      }
   return rooms;
    }
    public List<Room> getAvailableRoomsByDate(Date checkIn,Date checkOut) throws SQLException
    {
        String sql="SELECT *FROM rooms r "+
        "WHERE r.status = 'available' "+
        "AND NOT EXISTS (" +
        "SELECT 1 FROM booking b "+
        "WHERE b.room_id = r.roomId "+
        "AND b.booking_status = 'CONFIRMED' "+
        "AND b.check_in < ? "+
        "AND b.check_out > ? "+
        ") ";
        List<Room> rooms=new ArrayList<>();
        try(Connection con=DBConnection.getConnection();
           PreparedStatement stmt=con.prepareStatement(sql))
        {
            stmt.setDate(1, checkOut);
            stmt.setDate(2, checkIn);
        
          try(ResultSet res=stmt.executeQuery())
          {
            while(res.next())
            {
                Room room=new Room(
                res.getInt("roomId"),
                res.getString("roomNo"),
                res.getString("roomType"),
                res.getDouble("pricePerNight"),
                res.getInt("capacity"),
                res.getString("status") );    
                rooms.add(room);
            }
          }
        }
        return rooms;
    }
   
}
