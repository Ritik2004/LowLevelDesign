public class Predicate {
    public static void main(String[] args) {
        java.util.function.Predicate<Integer> p1 = (a) -> a % 2 == 0;
        System.out.println(p1.test(4));
        java.util.function.Predicate<String> p2 = (String str) -> {
            if(str.length() > 5){
                return true;
            }
            return false;
        };
        System.out.println(p2.test("Hello"));
    }
}
