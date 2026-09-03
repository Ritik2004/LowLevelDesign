package BookMyShow;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

enum SeatType {
    REGULAR,
    PREMIUM,
    RECLINER
}

enum SeatStatus {
    AVAILABLE,
    BOOKED,
    LOCKED
}

enum Bookingstatus{
    SUCCESS, FAILED
}

class User{
    String id;
    String name;
    User(String id,String name){
        this.id=id;
        this.name=name;
    }
}

class Movie{
    String id;
    String name;
    Movie(String id,String name){this.id=id;this.name=name;}
}

class Show{
    String id;
    Movie movie;
    List<Seat> seats;
    long startTime;
    Show(String id,Movie movie, List<Seat>seats, long startTime){
        this.id=id;
        this.movie=movie;
        this.seats=seats;
        this.startTime=startTime;
    }
}

class Seat{
    String id;
    SeatType type;
    SeatStatus status;
    long lockedAt;
    Seat(String id,SeatType type){
         this.id=id;
         this.type=type;
         this.status=SeatStatus.AVAILABLE;
         this.lockedAt= 0;
    }
    private final ReentrantLock lock = new ReentrantLock();

    boolean tryLock(){
        lock.lock();
        try{
               if(isAvailable()){
                 this.status=SeatStatus.LOCKED;
                 this.lockedAt=System.currentTimeMillis();
                 return true;
               }
          return false;
        }
        finally{
            lock.unlock();
        }
    }
    boolean isAvailable(){
       if(status==SeatStatus.AVAILABLE) return true;
       if(status == SeatStatus.LOCKED && lockExpired()){
        release();
        return true;
       }
       return false;
    }

    boolean lockExpired(){
        return System.currentTimeMillis() - lockedAt>5*60*1000;
    }

    void lock(){
       status=SeatStatus.LOCKED;
       lockedAt = System.currentTimeMillis();
    }

    void book(){
       status=SeatStatus.BOOKED;
    }

    void release(){
      status = SeatStatus.AVAILABLE;
      lockedAt=0;
    }
    
}
interface Payment{
    boolean pay(double amount);
}
class Upi implements Payment{
    public boolean pay(double amount){
        System.out.println("Paid " + amount + " via UPI");
        return true;
    }
}
class Card implements Payment{
    public boolean pay(double amount){
        System.out.println("Paid " + amount + " via Card");
        return true;
    }
    
}

class PaymentService{
    Payment payment;
  public void setPayment(Payment payment){
    this.payment=payment;
  }
   public boolean processPayment(double amount){
        if (payment == null) throw new IllegalStateException("No payment method set");
        return payment.pay(amount);
    }
}

class Booking{
    String id;
    User user;
    Show show;
    List<Seat>seat;
    Bookingstatus status;
    boolean paymentDone;
    Booking(String id, User user,Show show,List<Seat>seat,Bookingstatus status,boolean paymentDone){
        this.id = id;
        this.user = user;
        this.show = show;
        this.seat = seat;
        this.status = status;
        this.paymentDone = paymentDone;
    }
}

class BookingService{


    private final ReentrantLock lock = new ReentrantLock();
    private int bookingcount=1;

    List<Seat> getAvailableseats(Show show){
        List<Seat>available = new ArrayList<>();
        for(Seat seat:show.seats){
            if(seat.isAvailable()) available.add(seat);
        }
     return available;
    }


    boolean lockSeats(List<Seat>seats){
          List<Seat>lockedsofar = new ArrayList<>();
          for(Seat seat:seats){
            if(seat.tryLock()){
                lockedsofar.add(seat);
            }
            else{
                //rollback everythign if any of the set is not available
                for(Seat s:lockedsofar) s.release();
                return false;
            }
          }
          return true;
    }
    Booking bookSeats(User user, Show show,List<Seat>seats,PaymentService paymentService, double amount){
        boolean locked = lockSeats(seats);
        if(!locked){
            return new Booking("B"+bookingcount++,user,show,seats,Bookingstatus.FAILED,false);
        }
        for(Seat seat:seats){
            seat.book();
        }
        boolean paid = paymentService.processPayment(amount);
         if (!paid) {
        for (Seat s : seats) s.release(); 
        return new Booking("B"+bookingcount++,user,show,seats,Bookingstatus.SUCCESS,true);
    }
        for (Seat seat : seats) seat.book();
        return new Booking("B" + bookingcount++, user, show, seats, Bookingstatus.SUCCESS, true);
}

}
public class TicketBooking {

    public static void main(String[] args){
        Movie movie = new Movie("M1","Inception");
        User user = new User("U1","RITIK");
        List<Seat>seats = new ArrayList<>();
        seats.add(new Seat("S1", SeatType.PREMIUM));
        seats.add(new Seat("S2", SeatType.REGULAR));
        Show show = new Show("SH1",movie,seats,System.currentTimeMillis());
        PaymentService paymentService = new PaymentService();
        paymentService.setPayment(new Upi());
        BookingService service = new BookingService();
        System.out.println("Available:" + service.getAvailableseats(show).size());

        Booking booking = service.bookSeats(user, show, seats,paymentService,100);
        System.out.println("Booking status:"+ booking.status + ", payment"+ booking.paymentDone);
        System.out.println("Available after booking:" + service.getAvailableseats(show).size());
    }
    
}
