Multithreading in Java is a feature that allows multiple threads to execute tasks concurrently within a single process. It can improve responsiveness and performance when tasks can run independently.
Implemented in 2 ways - 
1) By extending thread class
2) By implemenung runnable interface.

By extending thread class
start() creates a new thread of execution
run() contains the task that the thread executes.

start()               	                                               run()
Starts a new thread	                                     Executes like a normal method when called directly
JVM schedules the new thread	                         Runs on the calling thread
Eventually invokes run() on the new thread	             Does not create a thread by itself
Cannot be called successfully a second                   Can be called directly multiple times  
 time on the same thread	

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
public class Main {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();

        t1.start(); // Starts a new thread
        t1.run();   // Normal method call
    }
}

Output
Thread-0
main



By implementing runnable interface
Implementing Runnable is preferred because Java supports single class inheritance, so extending Thread prevents us from extending another class. 
Runnable doesn't provide a start() method, we pass its implementation to a Thread object and call start() on that object. So we ned to create a thread in main to get acess
to start() method.


Short way to create a thread which shld be used is 
Example -  
Thread messageTask = new Thread(
            () -> System.out.println("Message sent successfully!")
        );


Runnable is a functional interface because it has one abstract method: run().
() -> System.out.println(...) is a lambda expression implementing run().
new Thread(...) accepts the Runnable task.
messageTask.start() starts a new thread that executes the task.

join - join() in Java makes the current thread wait until another thread finishes execution.

Thread lifeycle - 
