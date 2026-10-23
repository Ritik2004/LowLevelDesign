
public class Consumer {
     public static void main(String[] args) {
        java.util.function.Consumer<String> c1 = (c) -> System.out.println(c);
        c1.accept("Hello");

     }
}
