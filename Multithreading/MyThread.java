package Multithreading;

public class MyThread extends Thread {
    
    public void run(){
        System.out.println("Created Thread");
        for(int i=1;i<=5;i++){
            try{
              Thread.sleep(1000);
            }
            catch(InterruptedException e){
              System.out.println(e);
            }
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws InterruptedException{
         MyThread t1 = new MyThread();
         t1.start();
         t1.join();
         System.out.println("Thread is finished");

    }
    
}
