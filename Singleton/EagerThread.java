class EagerThread{
   ///in this we instantiate the variable in starting only
    private static volatile EagerThread instance = new EagerThread();

    private EagerThread(){
        System.out.println("Constructor called");
    }
    private static EagerThread getInstance(){
        return instance;
    }
    public static void main(String[] args){
        EagerThread s1 =EagerThread.getInstance();
       EagerThread s2 =EagerThread.getInstance();

        System.out.println(s1 == s2);
    }
}