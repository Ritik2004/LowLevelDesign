import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


enum VehicleType{
    Car,
    Bike,
    Truck
}

enum GateType{
    Entry,
    Exit
}

abstract class Vehicle{
    String number;
    VehicleType type;
}

class Car extends Vehicle{
     public Car(String number){
        this.number=number;
        this.type = VehicleType.Car;
     }
}

class Bike extends Vehicle{
   Bike(String number){
        this.number=number;
        this.type = VehicleType.Bike;
   }
}

class Truck extends Vehicle{
     Truck(String number){
        this.number=number;
        this.type=VehicleType.Truck;
     }
}

class ParkingSpot{
    String id;
    VehicleType allowedtype;
    boolean isOccupied;
    Vehicle vehicle;
    String floorid;
    ParkingSpot(String id,VehicleType allowedtype,String floorid){
        this.id=id;
        this.allowedtype=allowedtype;
        this.isOccupied=false;
        this.vehicle=null;
        this.floorid = floorid;
    }
}

class Ticket{
    String ticketid;
    Vehicle vehicle;
    LocalDateTime entrytime;
    String floorid;
    String spotId;
    String exitgate;
    Ticket(String ticketid,Vehicle vehicle){
      this.ticketid=ticketid;
      this.vehicle=vehicle;
      this.entrytime = LocalDateTime.now();
    }

}

class ParkingFloor{
    String id;
    List<ParkingSpot> spots;
       ParkingFloor(String id){
       this.id=id;
       this.spots = new ArrayList<>();
    }
    public void addSpot(ParkingSpot spot){
        spots.add(spot);
    }
     public ParkingSpot getSpotbyId(String spotid){
        for(ParkingSpot spot:spots){
            if(spot.id.equals(spotid)){
                return spot;
            }
        }
        return null;
    }
    //find parking sport on this floor for vehicle type
    public ParkingSpot findSpot(VehicleType type){
         for(ParkingSpot spot:spots){
            if(!spot.isOccupied && spot.allowedtype == type){
                return spot;
            }
         }
         return null;
    }
}

//different strategy to calalcalte fees

interface paymentStrategy{
    double calculatefee(LocalDateTime entrytime, LocalDateTime exittime);
}

class hourlyPayment implements paymentStrategy{

   private double hourrate;
   
   hourlyPayment(double hourrate){
        this.hourrate=hourrate;
    }
    public double calculatefee(LocalDateTime entrytime, LocalDateTime exittime){
         return 2.0;
    }
}
class flatPayment implements paymentStrategy{
   private double fixamount;
   
   flatPayment(double fixamount){
    this.fixamount=fixamount;
   } 
   public double calculatefee(LocalDateTime entrytime, LocalDateTime exittime){
         return fixamount;
    }
}

//different ways to make payment

interface makePaymentStrategy{
     boolean makePayment(double amount);
}

class UPIpayment implements makePaymentStrategy{
    public boolean makePayment(double amount){
        System.out.println("You paid "+ amount + " through UPI.");
        return true;
    }
}

class CARDpayment implements makePaymentStrategy{
    public boolean makePayment(double amount){
        System.out.println("You paid "+ amount + " through CARD.");
        return true;
    }
}

class CASHpayment implements makePaymentStrategy{
    public boolean makePayment(double amount){
        System.out.println("You paid "+ amount + " through CASH.");
        return true;
    }
}


//gates
abstract class Gate{
    String id;
    GateType gateType;
    ParkingLot parkingLot;

    Gate(String id, GateType gateType, ParkingLot parkingLot){
        this.id=id;
        this.gateType=gateType;
        this.parkingLot=parkingLot;
    }
}
class EntryGate extends Gate{
    EntryGate(String id, GateType gateType, ParkingLot parkingLot){
         super(id, gateType, parkingLot);
    }
      public Ticket enter(Vehicle vehicle){
        return parkingLot.parkVehicle(vehicle);
         
      }
 }
class ExitGate extends Gate{
    ExitGate(String id, GateType gateType, ParkingLot parkingLot){
         super(id, gateType, parkingLot);
    }
    public double exit(Ticket ticket, makePaymentStrategy makepayment){
        ticket.exitgate = id;
         return parkingLot.unparkVehicle(ticket, makepayment);
    }
}

//factory vehicle
class VehicleFactory{
    public static Vehicle create(VehicleType type,String number){
        switch (type) {
            case Car: return new Car(number);
            case Bike: return new Bike(number);
            case Truck : return new Truck(number);
        
            default : throw new IllegalArgumentException("Unknown type");
        }
    }
}


public class ParkingLot {
    private String name;
    // private List<ParkingSpot> spots;
    private List<ParkingFloor> floors;
    private paymentStrategy paymentstrategy;
    private makePaymentStrategy makepaymentstrategy;
    private String parkingway;
    public ParkingLot(String name,paymentStrategy paymentstrategy){
        this.name=name;
        this.floors = new ArrayList<>();
        this.paymentstrategy=paymentstrategy;
    }
     //add a spot to the lot
    public void addFloor(ParkingFloor floor){
        floors.add(floor);
    }
    // find available spot for vehicle type  floor by floor
    public ParkingSpot findSpot(VehicleType type){
      for(ParkingFloor floor:floors){
        ParkingSpot spot = floor.findSpot(type);
        if(spot!=null){
            return spot;
        }
      }
      return null; //no spot found
    }
    
    public double unparkVehicle(Ticket ticket,makePaymentStrategy makepaymentstrategy){
       for(ParkingFloor floor:floors){
       ParkingSpot spot = floor.getSpotbyId(ticket.spotId);
       if(spot!=null){
        spot.isOccupied=false;
        spot.vehicle=null;
        LocalDateTime exittime = LocalDateTime.now();
        double fee = paymentstrategy.calculatefee(ticket.entrytime, exittime);
        makepaymentstrategy.makePayment(fee);
         System.out.println("Your exit gate will be "+ ticket.exitgate);
        // System.out.println("Your fee is "+fee);
        return fee;
       }   
         
       }
       return 0;
    }
    //park vehicle get ticket
    public Ticket parkVehicle(Vehicle vehicle){
       ParkingSpot spot = findSpot(vehicle.type);
       if(spot == null){
        System.out.println("No spot available for"+ vehicle.type);
        return null;
       }
         synchronized(spot){//lock only this spot
             if(spot.isOccupied){
                //someone else took it find another
                return parkVehicle(vehicle);
             }
         }
       
       spot.isOccupied=true;
       spot.vehicle=vehicle;
       Ticket ticket = new Ticket("TKT"+UUID.randomUUID(),vehicle);
       ticket.spotId = spot.id;
       ticket.floorid = spot.floorid;
       System.out.println("Vehicle Parked! Ticket nos is :"+ ticket.ticketid + ".Your " + ticket.vehicle.type + " Parked at floor "+ ticket.floorid + " | " + ticket.spotId
       );
       return ticket;
    
    }


public static void main(String[] args){
    ParkingLot parkingLot = new ParkingLot("City center parking", new flatPayment(50.0));

    ParkingFloor floor1 = new ParkingFloor("F1");
    ParkingFloor floor2 = new ParkingFloor("F2");


    floor1.addSpot(new ParkingSpot("C1",VehicleType.Car,"F1"));
    floor2.addSpot(new ParkingSpot("T2",VehicleType.Truck,"F2"));

    parkingLot.addFloor(floor1);
    parkingLot.addFloor(floor2);
    
    // Vehicle vehicle = VehicleFactory.create(VehicleType.Car,"UP-1010-23");
    Vehicle vehicle2 = VehicleFactory.create(VehicleType.Truck, "MH-2020-11");

    //create gates
    EntryGate entryGate = new EntryGate("G1", GateType.Entry, parkingLot);
    ExitGate exitGate = new ExitGate("G2", GateType.Exit, parkingLot);

    // Ticket t1 = parkingLot.parkVehicle(car);
    Ticket t2 = entryGate.enter(vehicle2);

    try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
   exitGate.exit(t2, new UPIpayment()); 
    // parkingLot.unparkVehicle(t2, new UPIpayment());
}
}