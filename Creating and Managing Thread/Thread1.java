

   
class SMSThread extends Thread{
    @Override
    public void run(){
        try{
          Thread.sleep(2000);
          System.out.println("SMS sent successfully!");
        }
        catch(InterruptedException e){
            System.out.println("Thread interrupted: " + e.getMessage());

        }
    }
} 

class EmailThread extends Thread{
    @Override
    public void run(){
        try{
          Thread.sleep(3000);
          System.out.println("Email sent successfully!");
        }
        catch(InterruptedException e){
            System.out.println("Thread interrupted: " + e.getMessage());

        }
    }
}
class EtaCalculateThread extends Thread{
    @Override
    public void run(){
        try{
          Thread.sleep(4000);
          System.out.println("ETA calculated successfully!");
        }
        catch(InterruptedException e){
            System.out.println("Thread interrupted: " + e.getMessage());

        }
    }
}

public class Thread1 {
    public static void main(String[] args) {
        // Thread1 thread = new Thread1();
        // thread.start();

        SMSThread smsThread = new SMSThread();
        System.out.println("Starting SMS thread...");
        smsThread.start();

        EmailThread emailThread = new EmailThread();
        System.out.println("Starting Email thread...");
        emailThread.start();
 
        System.out.println("Starting ETA Calculation thread...");
        EtaCalculateThread etaThread = new EtaCalculateThread();
        etaThread.start();
         try{
            smsThread.join();
            emailThread.join();
            etaThread.join();
         }
         catch(InterruptedException e){
            System.out.println("Main thread interrupted: " + e.getMessage());
         }
    }
}
