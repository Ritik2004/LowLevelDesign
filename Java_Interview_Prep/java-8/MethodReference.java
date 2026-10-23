

interface Add {
    int add(int a, int b);
}

class Total {
    public static int add(int a, int b) {
        return a + b;
    }
}

public interface MethodReference {
   public static void main(String[] args){
        //    Add addnum = (a,b)-> a+b;
        // Add addnum = (a,b)-> Total.add(a,b);
        Add addnum1 = Total::add;
            int result = addnum1.add(5, 10);
            System.out.println("Result: " + result);
   }    
}


