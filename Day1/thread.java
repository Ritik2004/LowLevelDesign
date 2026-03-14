import java.lang.Thread;

class Mythread extends Thread{
    public void run(){
        System.out.println("This is my thread1");
        System.out.println("way1: " + Thread.currentThread().getName());
        System.out.println("way2: " + this.getName());
        System.out.println("way3: " + Thread.currentThread().getPriority());
    }
}

class MyRunnable implements Runnable{
     @Override
     public void run(){
        System.out.println("This is my thread2");
        System.out.println("way1: " + Thread.currentThread().getName());
        System.out.println("way2: " + Thread.currentThread().getPriority());
     }
}

public class thread {
    public static void main(String[] args) {
        Mythread t1 = new Mythread();
        t1.setName("MyThread");
        t1.start();

        Thread t2 = new Thread(new MyRunnable(),"Thread 2");
        t2.start();

        Thread t3 = new Thread(()->{
        System.out.println("This is my thread3");
        System.out.println("way1: " + Thread.currentThread().getName());
        },"Thread 3");
        t3.start();
    }
}
