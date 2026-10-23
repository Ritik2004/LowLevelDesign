
public class Supplier {
    public static void main(String[] args) {
        java.util.function.Supplier<Integer> s1 = () -> {
              return (int) (Math.random() * 100);
        };
        System.out.println(s1.get());       
    }
}
