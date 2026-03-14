interface MatchingStrategy{
    void match(String riderlocation);
}

class NearestDriver implements MatchingStrategy{
    @Override
    public void match(String riderlocation){
        System.out.println("Matching with nearest driver for rider at " + riderlocation);
    }
}

class ChepestDriver implements MatchingStrategy{
    @Override
    public void match(String riderlocation){
        System.out.println("Matching with cheapest driver for rider at " + riderlocation);
    }
}

class PriorityDriver implements MatchingStrategy{
    @Override
    public void match(String riderlocation){
        System.out.println("Matching with priority driver for rider at " + riderlocation);
    }
}

class RiderMatchingServie{
    private MatchingStrategy strategy;

    public void setStrategy(MatchingStrategy strategy){
        this.strategy = strategy;
    }
    public void matchRider(String riderLocation){
        strategy.match(riderLocation);
    }
}
public class Strategy{
    public static void main(String[] args){
        RiderMatchingServie matchingServie = new RiderMatchingServie();
        matchingServie.setStrategy(new ChepestDriver());
        matchingServie.matchRider("America");
        matchingServie.setStrategy(new NearestDriver());
        matchingServie.matchRider("India");
    }
}