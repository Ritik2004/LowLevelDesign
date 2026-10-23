package Java_Interview_Prep.Exception;

public class Finally {
     public static void main(String[] args){
         try{
      int a = 10;
      int b = 0;
      int c = a/b;
      System.out.println(c);
    }
    catch(ArithmeticException e){
      System.out.println("Error: Division by zero is not allowed."+e);
    }
    finally{
        System.out.println("Finally block is always executed");
     }  
} 
