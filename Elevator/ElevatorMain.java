import java.util.ArrayList;
import java.util.List;

enum State{
    Idle,
    MovingUp,
    MovingDown,
}

enum Direction{
   Up,
   Down,
}

class Door{
  private boolean isOpen;
  public void open(){
    isOpen = true;
  }
  public void close(){
    isOpen = false;
  }
}

class Floor{
    private int floorNos;
    private boolean upButton;
    private boolean downButton;

    Floor(int floorNos){
        this.floorNos=floorNos;
    }
    public void pressUp(){
        upButton = true;
    }
    public void pressDown(){
        downButton = true;
    }
    public int getFloor(){
        return floorNos;
    }
}


class Elevator{
    int id;
    int currentfloor;
    private State state;
    private Door door;
        public Elevator(int id) {
        this.id = id;
        this.currentfloor = 1;
        this.state = State.Idle;
        this.door = new Door();
    }

    public void MoveTofloor(int floor){
        if(floor>currentfloor){
            setState(state.MovingUp);
        }
        else{
            setState(state.MovingDown);
        }
         System.out.println("Elevator " + id + 
        " moved to floor " + floor);
        this.currentfloor = floor;
        setState(state.Idle);
    }
    public void openDoor(){
       door.open();
    }
    public void closeDoor(){
         door.close();
    }
    public void setState(State state){
         this.state = state;
    }
    public State getState(){
         return state;
    }
    public int getCurrecntfloor(){
        return currentfloor;
    }
    public int getId(){
        return id;
    }
}

class ElevatorRequest{
    private int sourceFloor;
    private int destinationFloor;
    private Direction direction;
    ElevatorRequest(int sourceFloor, int destinationFloor,Direction direction){
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }
   public int getSourceFloor() { return sourceFloor; }
    public int getDestinationFloor() { return destinationFloor; }
    public Direction getDirection() { return direction; }
}

class ElevatorController{
  private List<Elevator> elevators;
  public ElevatorController(){
    this.elevators = new ArrayList<>();
  }
  public void addElevator(Elevator elevator){
    elevators.add(elevator);
  }
  public void requestElevator(ElevatorRequest request){
    Elevator elevator = assignElevator(request);
    if(elevator!=null){
       processElevator(elevator, request);
    }
    else{
        System.err.println("No Elevator found!!");
    }
  }
  public Elevator assignElevator(ElevatorRequest request){
    for(Elevator elevator : elevators){
        if(elevator.getState() == State.Idle){
            return elevator;
        }
    }
    return null;
  }
  public void processElevator(Elevator elevator, ElevatorRequest request){
      elevator.closeDoor();
      elevator.MoveTofloor(request.getSourceFloor());
      elevator.openDoor();
      elevator.closeDoor();
      elevator.MoveTofloor(request.getDestinationFloor());
      elevator.openDoor();
      elevator.setState(State.Idle);
  }
}

public class ElevatorMain {
    public static void main(String[] args){
         ElevatorController controller = new ElevatorController();
         Elevator elevator1 = new Elevator(1);
         Elevator elevator2 = new Elevator(2);
         controller.addElevator(elevator1);
        //  controller.addElevator(elevator2);
         ElevatorRequest request = new ElevatorRequest(1,5,Direction.Up);
         controller.requestElevator(request);
    }
}