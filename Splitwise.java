import java.util.*;

class User{
    String id;
    String name;
    List<Expense> expenses;
    Map<User,Double> balance;
    User(String id, String name) {
    this.id = id;
    this.name = name;
    this.expenses = new ArrayList<>();
    this.balance = new HashMap<>();
}
}

interface SplitStrategy{
   List<Split> calculateSplit(double amount,List<User>users);
}
class EqualSplit implements SplitStrategy{
     public List<Split> calculateSplit(double amount,List<User>users){
       double perPerson = amount/users.size();
       List<Split> split = new ArrayList<>();
       for(User user:users){
        split.add(new Split(user, perPerson));
       }
       return split;
     }
}
class ExactSplit implements SplitStrategy{
    Map<User,Double> exactamounts;

    ExactSplit( Map<User,Double> exactamounts){
        this.exactamounts=exactamounts;
    }
     public List<Split> calculateSplit(double amount,List<User>users){
       List<Split> split = new ArrayList<>();
      for(User user:users){
        split.add(new Split(user, exactamounts.get(user)));
      }
      return split;
     }
}

class PercentageSplit implements SplitStrategy{
    private Map<User,Double> percentages;
    PercentageSplit(Map<User,Double> percentages){
        this.percentages=percentages;
    }
    public List<Split> calculateSplit(double amount,List<User>users){
        List<Split> split = new ArrayList<>();
        for(User user: users){
            split.add(new Split(user,amount*percentages.get(user)/100));
        }
        return split;
     }
}

class Expense{
    int groupid;
    String name;
    double amount; //how puch paid
    User paidby; //who paid
    List<User> users;//divide among whom
    SplitStrategy strategy; //hpw to split
    Expense(int groupid, String name, double amount, User paidby, List<User> users, SplitStrategy strategy){
        this.groupid = groupid;
        this.name = name;
        this.amount = amount;
        this.paidby = paidby;
        this.users = users;
        this.strategy = strategy;
    }
}

class Split{
    private User user;
    private double amount;
    Split(User user, double amount){
     this.user=user;
     this.amount=amount;
    }
    public User getUser(){
        return user;
    }
    public double getAmount(){
        return amount;
    }
}
class ExpenseManager{
    // userId -> (userId -> amount)
    // positive = you are owed, negative = you owe
    Map<String, Map<String, Double>> balances = new HashMap<>();

    void addExpense(Expense expense){
       List<Split> splits = expense.strategy.calculateSplit(expense.amount, expense.users);
       for(Split split:splits){
         User owes = split.getUser();
          if(!owes.equals(expense.paidby)){
            updatebalance(expense.paidby,owes,split.getAmount());
          }
       }
    }

    void updatebalance(User creditor, User debtor, double amount){
        // Step 1: Get creditor's page. If doesn't exist, create it.
        if(!balances.containsKey(creditor.id)){
            balances.put(creditor.id, new HashMap<>());
        }
        Map<String, Double> creditorPage = balances.get(creditor.id);

        // Step 2: Add amount to debtor's entry in creditor's page
        if(creditorPage.containsKey(debtor.id)){
            creditorPage.put(debtor.id, creditorPage.get(debtor.id) + amount);
        } else {
            creditorPage.put(debtor.id, amount);
        }

        // Step 3: Same thing for debtor's page, but negative
        if(!balances.containsKey(debtor.id)){
            balances.put(debtor.id, new HashMap<>());
        }
        Map<String, Double> debtorPage = balances.get(debtor.id);

        if(debtorPage.containsKey(creditor.id)){
            debtorPage.put(creditor.id, debtorPage.get(creditor.id) - amount);
        } else {
            debtorPage.put(creditor.id, -amount);
        }
    }

    void showBalance(User user){
        Map<String, Double> userBalances = balances.getOrDefault(user.id, new HashMap<>());
        for(Map.Entry<String, Double> entry : userBalances.entrySet()){
            if(entry.getValue() > 0){
                System.out.println(entry.getKey() + " owes " + user.name + ": " + entry.getValue());
            } else if(entry.getValue() < 0){
                System.out.println(user.name + " owes " + entry.getKey() + ": " + Math.abs(entry.getValue()));
            }
        }
    }
}

public class Splitwise {
  public static void main(String[] args) {
     User u1 = new User("1", "A");
     User u2 = new User("2","B");
     User u3 = new User("3","C");

     ExpenseManager expenseManager = new ExpenseManager();
     //alice pays 300 ,spliy equally among 3
     Expense e1 = new Expense(1, "dinner", 300, u1, Arrays.asList(u1, u2, u3), new EqualSplit());
     expenseManager.addExpense(e1);
     expenseManager.showBalance(u1);
  }
}
