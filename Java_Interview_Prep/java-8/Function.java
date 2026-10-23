

public class Function {
    public static void main(String[] args) {
        java.util.function.Function<String, Integer> f1 = (a)->a.length();
        System.out.println(f1.apply("Hello"));
        java.util.function.Function<Integer, Integer> f2  = (num) -> num * num;
        System.out.println(f2.apply(5));
    }
}
