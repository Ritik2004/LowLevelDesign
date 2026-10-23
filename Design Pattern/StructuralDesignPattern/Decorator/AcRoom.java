package StructuralDesignPattern.Decorator;

public class AcRoom extends RoomDecorator {

    public AcRoom(Room room) {
        super(room);
    }

    @Override
    public String getDescription() {
       return room.getDescription() + ", AC Room";
    }

    @Override
    public int getCost() {
        return room.getCost() + 2000;
    }
    
}
