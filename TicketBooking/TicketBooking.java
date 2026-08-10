package TicketBooking;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

enum SeatType{
    REGULAR,
    PREMIUM,
    VIP
}

enum SeatStatus{
    AVAILABLE,
    BOOKED,
    LOCKED
}

enum BookingStatus{
    SUCCESS,
    FAILURE
}



    class Movie{
        String movieId;
        String movieName;
        int duration;

        Movie(String movieId, String movieName, int duration){
            this.movieId=movieId;
            this.movieName=movieName;
            this.duration=duration;
        }
        public String getMovieId(){
            return this.movieId;
        }
        public String getMovieName(){
            return this.movieName;
        }
        public int getDuration(){
            return this.duration;
        }
    }
    class Screen{
        String screenId;
        List<Seat>seats;

        Screen(String screenId,List<Seat>seats){
            this.screenId=screenId;
            this.seats=seats;
        }

        public List<Seat> getAvailableSeat(){
            List<Seat> availableSeats = new ArrayList<>();
            for(Seat seat:seats){
                if(seat.isAvailable() || seat.islockexpired()){
                    availableSeats.add(seat);
                }
            }
            return availableSeats;
            
        }
    }

    class Seat{
       private String seatId;
       private SeatType seatType;
       private SeatStatus seatStatus;
       private int lockedAt;
       private static final long LOCK_Time = 5 * 60 * 1000; // 5 minutes in milliseconds
        Seat(String seatId,SeatType seatType,SeatStatus seatStatus){
            this.seatId=seatId;
            this.seatType=seatType;
            this.seatStatus=seatStatus;
            this.lockedAt=0;
        }
        public void Lock(){
            this.seatStatus=seatStatus.LOCKED;
            this.lockedAt=(int)System.currentTimeMillis();
        }
        public void book(){
            this.seatStatus=seatStatus.BOOKED;
        }
        public void release(){
            this.seatStatus=seatStatus.AVAILABLE;
            this.lockedAt=0;
        }
        public boolean isAvailable(){
            if(this.seatStatus==SeatStatus.LOCKED && islockexpired()){
                release();
            }
            return this.seatStatus==SeatStatus.AVAILABLE;
        }
        public boolean islockexpired(){
            if(this.lockedAt == 0){
                return false;
            }
            long currenttime = System.currentTimeMillis();
            long locktime = this.lockedAt;

            if(currenttime-locktime  > LOCK_Time){
                return true;
            }
            return false;
        }
        public String getSeatId(){
            return this.seatId;
        }
        public SeatType getSeatType(){
            return this.seatType;
        }
        public SeatStatus getSeatStatus(){
            return this.seatStatus;
        }
    }

  class Show{
    String showId;
    Movie movie;
    Screen screen;
    private LocalDateTime startTime;
    Show(String showId,Movie movie,Screen screen,LocalDateTime startTime){
        this.showId=showId;
        this.movie=movie;
        this.screen=screen;
        this.startTime=startTime;
    }
    public String getShowId(){
        return this.showId;
    }
    public Movie getMovie(){
        return this.movie;
    }
    public Screen getScreen(){
        return this.screen;
    }
    public LocalDateTime getStartTime(){
        return this.startTime;
    }
  }

  class Cinema{
       String cinemaId;
       String name;
       String city;
       List<Screen>screens;
       List<Show>shows;

       Cinema(String cinemaId,String name,String city,List<Screen>screens,List<Show>shows){
        this.cinemaId=cinemaId;
        this.name=name;
        this.city=city;
        this.screens=screens;
        this.shows=new ArrayList<>();
       }   
       public void addShow(Show show){
        shows.add(show);
       }
       
       public List<Show> getshowbyMovie(String movieId){
         List<Show> result = new ArrayList<>();
         for(Show show:shows){
            if(show.movie.movieId.equals(movieId)){
                result.add(show);
            }
         }
         return result;
       }
       public String getCity(){
        return this.city;
       }
  }
  class User{
    String userId;
    String name;
    User(String userId,String name){
        this.userId=userId;
        this.name=name;
    }
    public String getUserId(){
        return this.userId;
    }
    public String getName(){
        return this.name;
    }
  }
  class Booking{
    String bookingId;
    User user;
    Show show;
    List<Seat>seats;
    BookingStatus bookingStatus;
    private Payment payment;
    Booking(String bookingId, User user, Show show, List<Seat>seats, BookingStatus bookingStatus,Payment payment){
        this.bookingId=bookingId;
        this.user=user;
        this.show=show;
        this.seats=seats;
        this.bookingStatus=bookingStatus;
        this.payment=payment;
    }
    public void cancel(){
        this.bookingStatus=BookingStatus.FAILURE;
        for(Seat seat:seats){
            seat.release();
        }
    }
    public String getBookingId(){
        return this.bookingId;
    }
    public BookingStatus getBookingStatus(){
        return this.bookingStatus;
    }
  }
  class BookingSystem{
    List<Cinema>cinemas;
    List<Booking>bookings;
    private ReentrantLock lock;;
    BookingSystem(List<Cinema>cinemas,List<Booking>bookings){
        this.cinemas=cinemas;
        this.bookings=bookings;
        this.lock = new ReentrantLock();
    }
    public void addCinema(Cinema cinema){
        cinemas.add(cinema);
    }

    public List<Show> searchMovie(String city,String movieId)
    {  
         List<Show> result = new ArrayList<>();
         for(Cinema cinema:cinemas){
            if(cinema.city.equals(city)){
                 List<Show> shows = cinema.getshowbyMovie(movieId);
                 if(!shows.isEmpty()){
                    result.addAll(shows);
                 }
            }
         }      
         return result;
        }

      public List<Seat> getAvailableSeat(Show show){
        return show.screen.getAvailableSeat();

      }

      public boolean lockSeat(Seat seat){
        lock.lock();
        try{
            if(seat.isAvailable() || seat.islockexpired()){
                seat.Lock();
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
      }
      public Booking bookSeat(User user,Show show,List<Seat>seats){
        for(Seat seat:seats){
            if(!lockSeat(seat)){
                return null;
            }
        }
        Payment payment = new Payment("P001", 100.0, new CashPayment());
        Booking booking = new Booking("B001",user,show,seats,BookingStatus.SUCCESS,payment);
        bookings.add(booking);
      }
      public Booking bookSeat(User user,Show show,List<Seat>seats){
        for(Seat seat:seats){
            if(!lockSeat(seat)){
                return null;
            }
        }
        Payment payment = new Payment("P001", 100.0, new CashPayment());
        Booking booking = new Booking("B001",user,show,seats,BookingStatus.SUCCESS,payment);
        bookings.add(booking);
        for(Seat seat:seats){
            seat.book();
        }
        return booking;
      }
  }
  class Payment{
    String paymentId;
    double amount;
    PaymentMethod paymentMethod;
    Payment(String paymentId,double amount,PaymentMethod paymentMethod){
        this.paymentId=paymentId;
        this.amount=amount;
        this.paymentMethod=paymentMethod;
    }
    public boolean processPayment(){
        return paymentMethod.pay(amount);
    }
  }
  interface PaymentMethod{
    public boolean pay(double amount);
  }
  class CashPayment implements PaymentMethod{
    public boolean pay(double amount){
        System.out.println("Payment of "+amount+" done using cash");
        return true;
    }
  }
  class UpiPayment implements PaymentMethod{
    public boolean pay(double amount){
        System.out.println("Payment of "+amount+" done using UPI");
        return true;
    }
  }

class TicketBooking{
    public static void main(String[] args){
        // Create movies
    }}