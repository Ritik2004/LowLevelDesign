

public class Methods {
  public static void main(String[] args){
    //sleep
    // Thread sleep = new Thread(()->{
    //     System.out.println("This thread is going to sleep for 2 seconds");
    //   try{
    //     Thread.sleep(2000);
    //     System.out.println("This thread is sleeping for 2 seconds");
    //   }   
    //   catch(InterruptedException e){
    //     System.out.println("Thread is interrupted");
    //   }
    //   System.out.println("This thread is awake now");
    // });
    // sleep.start();

    //join
    Thread t2 = new Thread(()->{
        for(int i=0;i<5;i++){
            System.out.println("Worker Task"+i);
            try{
                Thread.sleep(500);
            }
            catch(InterruptedException e){
                System.out.println("Thread is interrupted");
        }
    }
    });
    t2.start();
    try{
        t2.join();
    }
    catch(InterruptedException e){
        System.out.println("Main thread is interrupted");
    }
    System.out.println("Worker thread has finished, main thread is resuming");
   
    //yield
    Thread longt = new Thread(()->{
        for(int i=0;i<5;i++){
            System.out.println("Long Task"+i);
            Thread.yield();
        }
    });
    longt.start();
}    
}
