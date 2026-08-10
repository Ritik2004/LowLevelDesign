package Lambda;


interface A{
    void run();
}

public class Lambda {
    // public static void main(String[] args){
    //     A obj = new A(){
    //         public void run(){
    //             System.out.println("Hi this is functional interface");
    //         }
    //     };
    //   obj.run();
    // }
       public static void main(String[] args){
        A obj =()-> System.out.println("Hi this is functional interface");
      obj.run();
    }
}
