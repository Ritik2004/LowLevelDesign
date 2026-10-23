import java.io.FileReader;
public class Trycatch {
    public static void main(String[] args){
//         try{
//   FileReader fr = new FileReader("abc.txt");
//         }
//         catch(Exception e){
//             System.out.println("File not found"+e);
//         }   

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
}
