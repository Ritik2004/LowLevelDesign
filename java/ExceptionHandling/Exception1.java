package java.ExceptionHandling;

import java.io.FileReader;

public class Exception1 {

   
     public static void main(String[] args) {
         int a[] = new int[5];
    try
    {
            
              System.out.println(a[8]);
    }
    catch(Exception e){ 
        System.out.println("Exception has occured" + e);
    }
}
}
