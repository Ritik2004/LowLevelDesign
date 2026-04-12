import java.util.*;
class Account{
    private int balance;
    private int limit;
    private ArrayList<Transaction> transaction;
    public Account(int balance, int limit){
        this.balance = balance;
        this.limit = limit;
        this.transaction = new ArrayList<>();
    }
    public int getBalance(){
        return balance;
    }
    public void setBalance(int balance){
        this.balance = balance;
    }
    public int getLimit(){
        return limit;
    }
    public void addTransaction(Transaction transaction){
        this.transaction.add(transaction);
    }
    public ArrayList<Transaction> getTransaction(){
        return this.transaction;
    }
}
class Transaction{
    private String type;
    private int amount;
    private int balance;
    Date date;
    public Transaction(String type, int amount, int balance, Date date){
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.date = date;
    }
   public void getTransaction(){
       System.out.println("Transaction are : ");
       System.out.println("Type: " + type);
       System.out.println("Amount: " + amount);
       System.out.println("Balance: " + balance);
       System.out.println("Date: " + date);
   }
}
class Card{
    private String cardNumber;
    private String userName;
    private int pin;
    private Account account;
    private boolean isBlocked;
    public Card(String cardNumber, String userName, int pin, Account account, boolean isBlocked){
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.pin = pin;
        this.account = account;
        this.isBlocked = isBlocked;
    }
    public String getCardNumber(){
        return cardNumber;
    }
    public Account getAccount(){
        return account;
    }
    public int getPin(){
        return pin;
    }
    public void setBlocked(boolean isBlocked){
        this.isBlocked = isBlocked;
    }
}
interface ATMState{
    void insertCard(ATM atm);
    void authenticatePin(ATM atm,int pin);
    void withdraw(ATM atm,int amount);
    void ejectCard(ATM atm);
}
class IdleState implements ATMState{
    public void insertCard(ATM atm){
        System.out.println("Card inserted");
        atm.setState(new CardInsertState()); //transition
    }
    public void authenticatePin(ATM atm,int pin){
        System.out.println("Insert Card First");
    }
    public void withdraw(ATM atm,int amount){
        System.out.println("Insert Card First");
    }
    public void ejectCard(ATM atm){
        System.out.println("No Card to eject");
    }
}
class CardInsertState implements ATMState{
    private int wrongattempt = 0;
     public void insertCard(ATM atm){
        System.out.println("Card already inserted");
    }
    public void authenticatePin(ATM atm,int pin){
        if(atm.getCard().getPin() == pin){
            System.out.println("Aunthenticated");
            atm.setState(new AunthenticatedState());
        }
        else{
         wrongattempt++;
         if(wrongattempt >= 3){
             System.out.println("Card blocked");
             atm.getCard().setBlocked(true);
             atm.setState(new IdleState());
         }

         else{
             System.out.println("Wrong pin attempt");
         }
        }
    }
    public void withdraw(ATM atm,int amount){
        System.out.println("Authenticate first");
    }
    public void ejectCard(ATM atm){
        atm.setState(new IdleState());
        System.out.println("Card ejected");
    }
}
class AunthenticatedState implements ATMState{
    public void insertCard(ATM atm){
        System.out.println("Card already inserted");
    }
    public void authenticatePin(ATM atm,int pin){

         System.out.println("Aunthentication successful");

    }
    public void withdraw(ATM atm,int amount){
         // do withdrawal logic
         Account account = atm.getCard().getAccount();
          synchronized(account){
          if(atm.getCard().getAccount().getBalance() >= amount && amount<=atm.getCard().getAccount().getLimit()){
          System.out.println("Dispensing " + amount);
          atm.getCard().getAccount().setBalance(atm.getCard().getAccount().getBalance() - amount);
          System.out.println("Balance remaining: " + atm.getCard().getAccount().getBalance());
          atm.setState(new IdleState());
          atm.getCard().getAccount().addTransaction(new Transaction("Withdrawal", amount, atm.getCard().getAccount().getBalance(), new Date()));
          }
          else{
              System.out.println("Insufficient balance");
              atm.setState(new IdleState());
          }
          }
    }
    public void ejectCard(ATM atm){
        atm.setState(new IdleState());
        System.out.println("Card ejected");
    }
}
class ATM{
   private ATMState state;
   private Card card;
     public ATM(){
         this.state = new IdleState();
   }
   public void setState(ATMState state){ this.state = state; }
   public Card getCard(){ return card; }

   public void insertCard(Card card){
    this.card=card;
    state.insertCard(this);
   }
   public void authenticatePin(int pin){
    state.authenticatePin(this, pin);
   }
   public void withdraw(int amount){
        state.withdraw(this, amount);
   }
   public void ejectCard(){
      state.ejectCard(this);
   }
   public void viewHistory(){
    Account account = card.getAccount();
    for(Transaction transaction: account.getTransaction()){
        transaction.getTransaction();
    }
   }
}

public class ATMMain{
  public static void main(String[] args){
    Account account = new Account(2000, 1000);
    Card card  = new Card("1234", "John", 123, account, false);
    ATM atm = new ATM();
    atm.insertCard(card);
    atm.authenticatePin(123);
    atm.withdraw(100);
    atm.ejectCard();
    atm.viewHistory();
  }
}
