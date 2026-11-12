package com.hotel;
import java.sql.Connection;
import java.sql.SQLException;
public class DBTest {
    public static void main(String[] args)
    {
        try(Connection con=DBConnection.getConnection())
        {
               System.out.println("mysql is connected successfully!");
        }
        catch(SQLException e)
        {
               System.out.println("mysql is failed!");
               System.out.println(e.getMessage());
        }
    }
    
}
