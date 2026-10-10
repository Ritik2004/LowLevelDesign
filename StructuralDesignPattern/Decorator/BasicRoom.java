package StructuralDesignPattern.Decorator;

public class BasicRoom implements Room {

    @Override
    public String getDescription() {
         return "Basic Room";
    }

    @Override
    public int getCost() {
        return 3000;
    }
    
}
