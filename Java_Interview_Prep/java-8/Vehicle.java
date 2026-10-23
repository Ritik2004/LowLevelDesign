public interface Vehicle{
    default void start(){
        System.out.println("Vehicle is starting");
    }
}