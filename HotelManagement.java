
import java.util.*;
enum RoomType{
    SINGLE,
    DOUBLE,
    DELUXE
}
enum BookingStatus{
    BOOKED,
    CONFIRMED,
    CANCELLED
}
enum ServiceType{
    RoomService,
    HouseKeeping,
}
enum RoomStatus{
    AVAILABLE,
    OCCUPIED,
    BOOKED
}

interface PricingStrategy{
    double getPrice();
}
class SingleRoomePrice implements PricingStrategy{
    public double getPrice(){
        return 100.0;
    }
}
class DoubleleRoomePrice implements PricingStrategy{
    public double getPrice(){
        return 200.0;
    }
}
class DuplexRoomePrice implements PricingStrategy{
    public double getPrice(){
        return 300.0;
    }
}

class Guest{
    int guestId;
    String name;
    Guest(int guestId, String name){
        this.guestId=guestId;
        this.name=name;
    }
    public String getDetails(){
        return "GuestId: "+guestId+" GuestName: "+name;
    }
}

class Room{
    int roomId;
    int roomnumber;
    RoomType roomtype;
    RoomStatus roomstatus;
    PricingStrategy pricingstrategy;
    Room(int roomId, int roomnumber, RoomType roomtype){
        this.roomId=roomId;
        this.roomnumber=roomnumber;
        this.roomtype=roomtype;
        this.roomstatus=RoomStatus.AVAILABLE;
        if(roomtype==RoomType.SINGLE){
            this.pricingstrategy=new SingleRoomePrice();
        }
        else if(roomtype==RoomType.DOUBLE){
            this.pricingstrategy=new DoubleleRoomePrice();
        }
        else if(roomtype==RoomType.DELUXE){
            this.pricingstrategy=new DuplexRoomePrice();
        }
    }
    public PricingStrategy getPricingStrategy(){
        return pricingstrategy;
    }
    public void serPricingStrategy(PricingStrategy pricingstrategy){
        this.pricingstrategy=pricingstrategy;
    }

    public double pricepernigth(){
        return pricingstrategy.getPrice();
    }
    public double getPrice(int days){
        return days*pricepernigth();
    }
    public boolean isAvailable(){
        return roomstatus==RoomStatus.AVAILABLE;
    }

}
class Bill{
    int billId;
    int billnumber;
    double roomcharge;
    double servicecharge;
    double totalcharge;
    Bill(int billId, int billnumber, double roomcharge, double servicecharge, double totalcharge){
        this.billId=billId;
        this.billnumber=billnumber;
        this.roomcharge=roomcharge;
        this.servicecharge=servicecharge;
        this.totalcharge=totalcharge;
    }
    public double getTotalCharge(){
        return roomcharge+servicecharge;
    }
}
class Service{
   int serviceid;
   Booking booking;
   ServiceType servicetype;
   Service(int serviceid, Booking booking, ServiceType servicetype){
       this.serviceid=serviceid;
       this.booking=booking;
       this.servicetype=servicetype;
   }
   public void createservice(){

   }
   public void completeservice(){

   }
}
class Booking{
    int bookingid;
    Room room;
    Guest guest;
    Date checkindate;
    Date checkoutdate;
    BookingStatus bookingstatus;
    Booking(int bookingid, Room room, Guest guest, Date checkindate, Date checkoutdate, BookingStatus bookingstatus){
        this.bookingid=bookingid;
        this.room=room;
        this.guest=guest;
        this.checkindate=checkindate;
        this.checkoutdate=checkoutdate;
        this.bookingstatus=bookingstatus;
    }
    public void confirmbooking(){
        bookingstatus=BookingStatus.CONFIRMED;
    }
    public void cancelbooking(){
        bookingstatus=BookingStatus.CANCELLED;
    }
    public int getduration(){
        long diff =  checkoutdate.getTime()-checkindate.getTime();
        return (int)(diff/(1000*60*60*24));
    }
}
class HotelManagementSystem{
    int hotelid;
    String hotelname;
    List<Room> rooms;
    List<Booking> bookings;
    HotelManagementSystem(int hotelid, String hotelname, List<Room> rooms){
        this.hotelid=hotelid;
        this.hotelname=hotelname;
        this.rooms=rooms;
        this.bookings = new ArrayList<Booking>();
    }
    public void addroom(Room room){
        rooms.add(room);
    }
    public void removeroom(Room room){
        rooms.remove(room);
    }
    public List<Room> getRoom(){
        return rooms;
    }
    public Booking bookRoom(Guest guest, int roomnumber, Date checkindate,Date checkoutdate){
      //Find room
      Room room = null;
      for(Room r:rooms){
        if (r.roomnumber == roomnumber && r.isAvailable()){
            room = r;
            break;
        }
      }
      if(room == null){
        System.out.println("Room not found");
        return null;
      }

      synchronized(room){
        if(!room.isAvailable()){
          System.out.println("Room not available");
          return null;
        }
      }

      //mark room as booked
      room.roomstatus = RoomStatus.BOOKED;
     //created booking
     Booking booking = new Booking(bookings.size()+1,room,guest,checkindate,checkoutdate,BookingStatus.BOOKED);
     bookings.add(booking);
     return booking;
    }
    public Bill checkout(int bookingid,double servicecharge){
        Booking booking = null;
        for(Booking b:bookings){
            if(b.bookingid == bookingid){
                booking  = b;
                break;
            }
        }
        if (booking == null) {
          System.out.println("Booking not found");
          return null;
      }
      //calculate charge
      int days = booking.getduration();
      double roomcharge = booking.room.getPrice(days);


      //generate bill
      Bill bill = new Bill(bookingid, bookingid,roomcharge,servicecharge, roomcharge+servicecharge);
       // update status
       booking.bookingstatus = BookingStatus.CONFIRMED;
       booking.room.roomstatus = RoomStatus.AVAILABLE;

       return bill;
    }
}

public class HotelManagement{
    public static void main(String[] args) {
        HotelManagementSystem hotel=new HotelManagementSystem(1, "Hotel Management System", new ArrayList<Room>());
        Room room1=new Room(1, 101, RoomType.SINGLE);
        Room room2=new Room(2, 102, RoomType.DOUBLE);
        Room room3=new Room(3, 103, RoomType.DELUXE);
        hotel.addroom(room1);
        hotel.addroom(room2);
        hotel.addroom(room3);
        Guest guest1=new Guest(1, "John");
        Booking booking1=hotel.bookRoom(guest1, 102, new Date(2023, 1, 1),new Date(2023, 1, 3));
        System.out.println("Booking created: " + booking1.bookingid);
       System.out.println("Room status: " + booking1.room.roomstatus);
        booking1.confirmbooking();
        Bill bill = hotel.checkout(booking1.bookingid, 50.0);
      System.out.println("Total charge: " + bill.getTotalCharge());
      System.out.println("Room status after checkout: " + room1.roomstatus);

    }
}
