@java.lang.FunctionalInterface
public interface FunctionalInterface {
    //    void method();
    //    default void method2(){
    //        System.out.println("Default implementation of method2");
    //    }
    //    default void method3(){
    //        System.out.println("Default implementation of method3");
    //    }
    int add(int a, int b);
}

class Main {
    public static void main(String[] args) {
        // FunctionalInterface functionalInterface = new FunctionalInterface() {
        //     @Override
        //     public int add(int a, int b) {
        //         return a + b;
        //     }
        // };

        // Using lambda expression
        // there is only one method so we can use lambda expression to implement the method
        FunctionalInterface functionalInterface = (a, b) -> a + b;
         int result = functionalInterface.add(5, 10);
        System.out.println("Result: " + result);
    }
}

