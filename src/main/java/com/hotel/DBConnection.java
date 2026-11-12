package com.hotel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    public static Connection getConnection() throws SQLException
    {
        String url="jdbc:mysql://localhost:3306/hotel_reservation";
        String username=System.getenv("DB_USERNAME");
        String password=System.getenv("DB_PASSWORD");

        return DriverManager.getConnection(url,username,password);
    }
    
}
