package Multithreading;

public class RunnableThread implements Runnable {
    public void run(){ 
        System.out.println("Created Thread by runnable interface");
    }

    public static void main(String[] args){
        RunnableThread r1 = new RunnableThread();
        Thread t1 = new Thread(r1);
        t1.start();
    }     
} 