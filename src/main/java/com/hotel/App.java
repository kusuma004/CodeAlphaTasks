package com.hotel;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
        
        Scanner sc=new Scanner(System.in);
        int choice=0;
        while(choice!=7){
        System.out.println("*****HOTEL RESERVATION SYSTEM*****");
        System.out.println("1.View Available Rooms");
        System.out.println("2.Create Room Reservation");
        System.out.println("3.Cancel Room Reservation");
        System.out.println("4.View Reservation Details");
        System.out.println("5.Process Payment");
        System.out.println("6.Search Rooms By Date");
        System.out.println("7.Exit Application");
        System.out.println("***********************************");
        System.out.println("Enter your choice: ");
        
        try
        {
          choice=Integer.parseInt(sc.nextLine());

        }catch(NumberFormatException e)
        {
          System.out.println("Invalid input! please enter a number form 1 to 7.");
          continue;
        }
        if(choice < 1 || choice > 7)
        {
            System.out.println("Invalid choice! please enter a number from 1 to 7.");
            continue;
        }
        
        switch(choice)
        {
            case 1:
                  ViewAvailableRooms();
                  break;
            case 2:
                CreateRoomReservation(sc);
                break;
            case 3:
                cancelRoomReservation(sc);
                break;
            case 4:
                  viewReservationDetails(sc);
                break;
            case 5:
                processPayment(sc);
                break;
            case 6:
                searchRoomsByDate(sc);
                 break;
            case 7:
                System.out.println("Thankyou for visiting Hotel Reservation System!");
                break;
           default:
                System.out.println("Invalid choice");

        }
     }
        sc.close();
    }
    public static  void ViewAvailableRooms()
    {
        
         try 
         {
             RoomDAO roomDAO=new RoomDAO();
            List<Room> rooms=roomDAO.getAvailableRooms();
            System.out.println("*****AVAILABLE ROOMS*****");
            if(rooms.isEmpty())
            {
                System.out.println("No rooms are currently available");
            }
            else{
                for(Room room:rooms)
                {
                    System.out.println("Room Number:"+room.getRoomNo());
                    System.out.println("Room Type:"+room.getRoomType());
                    System.out.println("pricePerNight:"+room.getPricePerNight());
                    System.out.println("capacity:"+room.getCapacity());
                    System.out.println("***********************");
                }
            }
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
             System.out.println("Unable to retrieve rooms from the database...");
            e.printStackTrace();
        }
          
    }
    public static void CreateRoomReservation(Scanner sc)
    {
        try
        {
        System.out.println("*****CREATE ROOM RESERVATION*****");

        System.out.println("Enter Customer Name:");
        String customerName=sc.nextLine().trim();
        if(customerName.isEmpty())
        {
            System.out.println("Customer Name cannot be empty!...");
            return;
        }
        if(!customerName.matches("[A-Za-z ]+"))
        {
            System.out.println("Invalid customer name! please enter name with letters and spaces only...");
            return;
        }
        System.out.println("Enter Email:");
        String email=sc.nextLine().trim();
        if(email.isEmpty())
        {
            System.out.println("Email cannot be empty!...");
            return;
        }
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        {
            System.out.println("Invalid email format!please enter valid email...");
            return;
        }
        System.out.println("Enter room Id:");
        int roomId;
        try
        {
            roomId=Integer.parseInt(sc.nextLine());
        }catch(NumberFormatException e)
        {
            System.out.println("Invalid room Id! please enter numbers only...");
            return;
        }
        if(roomId <= 0)
        {
            System.out.println("Invalid Room ID! room id must be positive numbers...");
            return;
        }
        Date checkIn;
        Date checkOut;
        try
        {
        System.out.println("Enter check-in date(YYYY-MM-DD):");
        String checkInInput=sc.nextLine().trim();
        System.out.println("Enter check-out date(YYYY-MM-DD)");
        String  checkOutInput=sc.nextLine().trim();
          LocalDate checkInLocal = LocalDate.parse(checkInInput.toString());
          LocalDate checkOutLocal = LocalDate.parse(checkOutInput.toString());
           checkIn = Date.valueOf(checkInLocal);
           checkOut = Date.valueOf(checkOutLocal);

        }catch(DateTimeParseException e)
        {
            System.out.println("Invalid date! Please enter valid dates in YYYY-MM-DD format.");
            return;
        }

         if(!checkOut.after(checkIn))
        {
            System.out.println("check-out date must be after check-in date");
            return;
        }
        Date today = new Date(System.currentTimeMillis());

       if (checkIn.before(today)) {
              System.out.println("Check-in date cannot be in the past.");
              return;
            }
       
        RoomDAO roomDAO=new RoomDAO();
        List<Room> rooms=roomDAO.getAvailableRoomsByDate(checkIn,checkOut);
        Room selectedRoom=null;
        for(Room room: rooms)
        {
            if(room.getRoomId()==roomId)
            {
                selectedRoom=room;
                break;
            }
        }
        if(selectedRoom==null)
        {
            System.out.println("Room not found or not available..");
            return;
        }
        BookingDAO bookingDAO=new BookingDAO();
        boolean available=bookingDAO.isRoomAvailable(roomId, checkIn, checkOut);
        if(!available)
        {
            System.out.println("Room is already booked for these dates.please chose different dates...");
            return;
        }

        long milliseconds=checkOut.getTime()-checkIn.getTime();
        long nights=milliseconds/(1000L*60*60*24);
        double totalAmount=selectedRoom.getPricePerNight()*nights;
        System.out.println("Number of Night:"+nights);
        System.out.println("Total Amount:"+totalAmount);
        Booking booking =new Booking(0,customerName,email,roomId,checkIn,checkOut,totalAmount,"CONFIRMED");
       
        boolean sucess=bookingDAO.createBooking(booking);
        if(sucess)
        {
            System.out.println("Reservation created successfully...");
        }
        else
        {
            System.out.println("Reservation could not be created...");
        }
    }
    catch(IllegalArgumentException e)
    {
        System.out.println("Invalid input! Please enter a valid room ID and dates in YYYY-MM-DD format.");
    }
    catch(SQLException e)
    {
        System.out.println("Database error while creating reservation..");
        e.printStackTrace();
    }
 }
 public static void viewReservationDetails(Scanner sc)
 {
    try
    {
        System.out.println("*****VIEW RESERVATION DETAILS*****");
        System.out.println("Enter booking id:");
        int bookingid=Integer.parseInt(sc.nextLine());
        BookingDAO bookingDAO=new BookingDAO();
        Booking booking=bookingDAO.getBookingById(bookingid);
        if(booking==null)
        {
            System.out.println("Booking not found...");
            return;
        }
        System.out.println("Booking id:"+booking.getBookingId());
        System.out.println("Customer Name:"+booking.getCustomerName());
        System.out.println("Email:"+booking.getEmail());
        System.out.println("Room Id:"+booking.getRoomId());
        System.out.println("check In Date:"+booking.getCheckIn());
        System.out.println("check Out Date:"+booking.getCheckOut());
        System.out.println("total Amount:"+booking.getTotalAmount());
        System.out.println("booking Status:"+booking.getBookingStatus());

    }catch(NumberFormatException e)
    {
        System.out.println("please enter a valid booking id...");
    }
    catch(SQLException e)
    {
        System.out.println("Error while retrieving booking details...");
        e.printStackTrace();
    }
 }
 public static void cancelRoomReservation(Scanner sc)
 {
    try
    {
        System.out.println("*****CANCEL ROOM RESERVATION*****");
        System.out.println("Enter booking id:");
        int bookingId=Integer.parseInt(sc.nextLine());
        BookingDAO bookingDAO=new BookingDAO();
        boolean succes=bookingDAO.cancelBooking(bookingId);
        if(succes)
        {
            System.out.println("Booking Cancelled Successfully!...");
        }
        else{
        System.out.println("Booking could not be cancelled. It may be paid, already cancelled, or invalid...");
        }
    }
    catch(NumberFormatException e)
    {
        System.out.println("please enter a valid booking id...");
    }
    catch(SQLException e)
    {
        System.out.println("Error while cancelling reservation...");
        e.printStackTrace();
    }
 }

 public static void processPayment(Scanner sc)
 {
    try
    {
        System.out.println("*****PROCESS PAYMENT*****");
        System.out.println("Enter booking id:");
        int bookingId=Integer.parseInt(sc.nextLine());
        BookingDAO bookingDAO=new BookingDAO();
        Booking booking=bookingDAO.getBookingById(bookingId);
        if(booking==null)
        {
            System.out.println("booking not found...");
            return;
        }
        if(!booking.getBookingStatus().equals("CONFIRMED"))
        {
            System.out.println("payment cannot be processed for a cancelled booking...");
            return;
        }
        System.out.println("Total Amount: "+booking.getTotalAmount());
        System.out.println("confirm payment? (yes/no)");

        String choice=sc.nextLine();
        if(choice.equalsIgnoreCase("yes"))
        {
            boolean success=bookingDAO.processPayment(bookingId);
            if(success)
            {
                System.out.println("Payment Successful...");
            }
            else
            {
                System.out.println("payment failed or already processed...");
            }
        }
        else
        {
            System.out.println("Payment cancelled by user...");
        }

    }
    catch(NumberFormatException e)
    {
       System.out.println("please enter a valid booking id...");
    }
    catch(SQLException e)
    {
        System.out.println("Database error while processing payment...");
        e.printStackTrace();
    }
 }
 public static void searchRoomsByDate(Scanner sc) throws SQLException
 {
    Date checkIn;
    Date checkOut;
    try
    {
    System.out.println("Enter the check_in date(YYYY-MM-DD):");
    checkIn = Date.valueOf(sc.nextLine());
    System.out.println("Enter the check_out date(YYYY-MM-DD):");
    checkOut=Date.valueOf(sc.nextLine());
    }catch(IllegalArgumentException e)
    {
        System.out.println("Invalid date! Please enter a valid date in YYYY-MM-DD format.");
        return;
    }
    Date today=new Date(System.currentTimeMillis());
    if(checkIn.before(today))
    {
      System.out.println("check_in date cannot be in the  past");
      return;
    }
   if(!checkOut.after(checkIn))
   {
       System.out.println("check out date must be after check in date.....");
       return;
   }
  RoomDAO roomDAO=new RoomDAO();
  List<Room> rooms=roomDAO.getAvailableRoomsByDate(checkIn, checkOut);
  if(rooms.isEmpty())
  {
    System.out.println("No rooms available for these dates....");
  }
  else{
    System.out.println("Rooms available for the selected dates: ");
    for(Room room : rooms)
    {
        System.out.println(room);
    }
  }
    
    
 }
}
