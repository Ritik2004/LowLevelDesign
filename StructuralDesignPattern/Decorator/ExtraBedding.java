package StructuralDesignPattern.Decorator;

public class ExtraBedding extends RoomDecorator {

    public ExtraBedding(Room room){
        super(room);
    }
  
    @Override
    public String getDescription() {
        return room.getDescription() + ", Extra Bedding";
    }
    @Override
    public int getCost() {
        return room.getCost() + 1000;
    }
}
