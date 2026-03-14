

public class DirectRunnable {
     public static void main(String[] args) {
        // Runnable task = new Runnable() {
        //     @Override
        //     public void run() {
        //         System.out.println("This is a direct Runnable example.");
        //     }
        // };
        Runnable task = () -> {
            try{
                Thread.sleep(2000);
                System.out.println("This is a direct Runnable example.");
            }
            catch(Exception e){
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        };
        Thread thread = new Thread(task);
        thread.start();
}
}
