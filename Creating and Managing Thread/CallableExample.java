import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class ETACalculation implements Callable<String> {
    public String call() throws Exception {

        // Simulate some work to calculate ETA
        Thread.sleep(10000); // Simulating time-consuming task
        return "ETA: 15 minutes"; // Returning a dummy ETA value
    }
}
class SMS implements Runnable {
    @Override
    public void run() {
        try{
            Thread.sleep(1000);
            System.out.println("SMS sent successfully!");
        }
        catch(InterruptedException e){
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }
}

class Email implements Runnable {
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

public class CallableExample {
    public static void main(String[] args) {
        SMS smsTask = new SMS();
        Email emailTask = new Email();
        FutureTask<String> etaTask = new FutureTask<>(new ETACalculation());
        Thread smsThread = new Thread(smsTask);
        Thread emailThread = new Thread(emailTask);
        Thread etaThread = new Thread(etaTask);

        smsThread.start();
        emailThread.start();
        etaThread.start();

        try {
            smsThread.join();
            emailThread.join();
            etaThread.join();
            System.out.println(etaTask.get());
        } catch (Exception e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }
}
