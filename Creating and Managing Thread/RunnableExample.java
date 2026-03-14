import java.util.*;
class SmsTask implements Runnable {
    @Override
    public void run() {
        try{
            Thread.sleep(10000);
            System.out.println("SMS sent successfully!");
        }
        catch(InterruptedException e){
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }
}
class EmailTask implements Runnable {
    @Override
    public void run() {
        try{
            Thread.sleep(3000);
            System.out.println("Email sent successfully!");
        }
        catch(InterruptedException e){
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }
}

public class RunnableExample {
    public static void main(String[] args) {
        System.out.println("This is a Runnable example.");

        SmsTask smsTask = new SmsTask();
        EmailTask emailTask = new EmailTask();
        Thread smsThread = new Thread(smsTask);
        Thread emailThread = new Thread(emailTask);
        System.out.println("Starting SMS thread...");
        smsThread.start();
        System.out.println("Starting Email thread...");
        emailThread.start();
        try{
            smsThread.join();
                emailThread.join();
        }
        catch(InterruptedException e){
            System.out.println("Main thread interrupted: " + e.getMessage());
        }
    }                   
}
