public class ThreadSafeSingleton{
    private static ThreadSafeSingleton instance = null;
  private ThreadSafeSingleton(){
    System.out.println("Contructor called");
  }
  public static ThreadSafeSingleton getInstance(){
    if(instance == null){//first check
        //this avoid unnecessary locking 
        synchronized(ThreadSafeSingleton.class){
        if(instance == null){//second check
            //ensure only one object is created after acquring lock
             instance = new ThreadSafeSingleton();
        }
    }
}
return instance;

  }
  public static void main(String[] args){
    ThreadSafeSingleton s1 = ThreadSafeSingleton.getInstance();
    ThreadSafeSingleton s2 = ThreadSafeSingleton.getInstance();
    System.out.println(s1 == s2);
  }
}