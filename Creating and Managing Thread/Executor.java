import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class EmailService{
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    public static void sendEmail(String reception){
        executor.execute(()->{
            System.out.println("Sending email to " + reception);
            try{
             Thread.sleep(2000);
             System.out.println("Email sent to " + reception);
            }
            catch(Exception e){
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        });
    }
    public static void shutdown() {
        executor.shutdown();
    }
}

public class Executor {
    public static void main(String[] args) {
        for(int i=1; i<=25; i++){
            String reception = "user" + i + "@example.com";
            EmailService.sendEmail(reception);
        }  
        EmailService.shutdown();
}
}
