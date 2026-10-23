package StructuralDesignPattern.Decorator;

public class Main {
    public static void main(String[] args){
        Room basicRoom = new BasicRoom();
        System.out.println(basicRoom.getDescription() + " Cost: " + basicRoom.getCost());

         basicRoom = new BreakfastRoom(basicRoom);
         basicRoom = new AcRoom(basicRoom);
         basicRoom = new ExtraBedding(basicRoom);
        System.out.println(basicRoom.getDescription() + " Cost: " + basicRoom.getCost());
    }
    
}
