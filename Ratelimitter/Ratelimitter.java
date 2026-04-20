
import java.util.*;
enum UserTier{
    FREE,
    PREMIUM
}
class RateLimiterConfig{
    private int maxrequests;
    private long maxwindowsize;
   RateLimiterConfig(int maxrequests, long maxwindowsize){
    this.maxrequests=maxrequests;
    this.maxwindowsize=maxwindowsize;
   }
   public int getmaxrequests(){
    return maxrequests;
   }
   public long getmaxwindowsize(){
    return maxwindowsize;
   }
}
class User{
    private String userId;
    private UserTier tier;

    User(String userId,UserTier tier){
        this.userId=userId;
        this.tier=tier;
    }
    public String getName(){
        return userId;
    }
    public UserTier getTier(){
        return tier;
    }
}

interface RatelimiterStrategy{
    boolean isAllowed(String userId, RateLimiterConfig config);
}

class FixwindowStrategy implements RatelimiterStrategy{
    private Map<String, long[]>windows = new HashMap<>(); //key->[windowstart,count]
   public boolean isAllowed(String userId, RateLimiterConfig config){
       long now = System.currentTimeMillis();
       long currentwindow = now - (now % config.getmaxwindowsize());

       long[] entry = windows.get(userId);
       if(entry == null || entry[0]!=currentwindow){
         windows.put(userId, new long[]{currentwindow,1});
         return true;
       }
       if(entry[1]<config.getmaxrequests()){
        entry[1]++;
        return true;
       }
        return false;
   }
}
class SlidingWindowlog implements RatelimiterStrategy{
     private Map<String, Queue<Long>>windows = new HashMap<>();
     public boolean isAllowed(String userId, RateLimiterConfig config){
          long now = System.currentTimeMillis();
          long windowstart = now-config.getmaxwindowsize();
          Queue<Long>queue = windows.get(userId);
          if(queue == null){
            queue = new LinkedList<>();
            windows.put(userId,queue);
          }
          while(!queue.isEmpty() && queue.peek()<windowstart){
            queue.poll();
          }
          if(queue.size()<config.getmaxrequests()){
            queue.add(now);
            return true;
          }
          return false;
     }
}
class RatelimitterMain {
   private RatelimiterStrategy strategy;
   private Map<UserTier, RateLimiterConfig> tierConfigs;
   RatelimitterMain(RatelimiterStrategy strategy) {
        this.strategy = strategy;
        this.tierConfigs = new HashMap<>();
    }
   public void addTierconfig(UserTier usertier, RateLimiterConfig config){
    tierConfigs.put(usertier,config);
   }

   public void setstrategy(RatelimiterStrategy strategy){
      this.strategy=strategy;
   }
    public boolean isAllowed(User user){
     RateLimiterConfig config = tierConfigs.get(user.getTier());
       return strategy.isAllowed(user.getName(), config);
    }
}  

class Ratelimitter{
    public static void main(String[] args) {
        RateLimiterConfig freeconfig = new RateLimiterConfig(3,10000);
        RateLimiterConfig premiumconfig = new RateLimiterConfig(10,10000);
            
        RatelimitterMain ratelimitter = new RatelimitterMain(new FixwindowStrategy());
        ratelimitter.addTierconfig(UserTier.FREE, freeconfig);
        ratelimitter.addTierconfig(UserTier.PREMIUM, premiumconfig);

        User freeuser = new User("Ritik", UserTier.FREE);
        User premiUser = new User("Rahul",UserTier.PREMIUM);

        System.out.println("--- FREE USER (limit 3) ---");
        for(int i=0;i<=5;i++){
            boolean allowed  = ratelimitter.isAllowed(freeuser);
            System.out.println("Request"+(i+1) + ":" +(allowed?"Allowed":"Denied"));
        }

           System.out.println("\n--- PREMIUM USER (limit 10) ---");
        for (int i = 0; i < 5; i++) {
            boolean allowed =  ratelimitter.isAllowed(premiUser);
            System.out.println("Request " + (i+1) + ": " + 
                (allowed ? "Allowed" : "Denied"));
        }
    }
}