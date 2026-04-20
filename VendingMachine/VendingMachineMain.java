import java.util.HashMap;
import java.util.Map;

class Item{
    private String name;
    private int price;

    public Item(String name, int price, int quantity){
        this.name=name;
        this.price=price;
    }
    public String getName(){
        return this.name;
    }
    public int getPrice(){
        return this.price;
    }
}

abstract class  VendingMachineState{
   abstract void insertCoin(VendingMachine vm, int amount);
   abstract void selectItem(VendingMachine vm, String slotid);
    abstract void dispenseItem(VendingMachine vm);
    abstract void returnCoin(VendingMachine vm);
}

class NoCoinState extends VendingMachineState{
    @Override
    void insertCoin(VendingMachine vm, int amount) {
        // logic to insert coin
        PaymentStrategy strategy = vm.getPaymentstrategy();
        boolean success = strategy.pay(amount);
        if(success){
        vm.addAmount(amount);
        System.out.println("Coin Inserted:" + amount);
        vm.setState(vm.getHasCoinState());
    } else{
        System.out.println("Payment failed");
    }
}

    @Override
    void selectItem(VendingMachine vm,String slotid) {
        // logic to select item
        System.out.println("Please insert coin first!");
    }

    @Override
    void dispenseItem(VendingMachine vm) {
        // logic to dispense item
        System.out.println("Please insert coin first!");
    }

    @Override
    void returnCoin(VendingMachine vm) {
        // logic to return coin
        System.out.println("No coin to return!");
    }
} 

class HasCoinState extends VendingMachineState{
     void insertCoin(VendingMachine vm, int amount) {
        // logic to insert coin
    
         vm.addAmount(amount);
    }

    @Override
    void selectItem(VendingMachine vm, String slotid) {
        // logic to select item
       Slot slot = vm.getSlot(slotid);
       if(slot == null || !slot.hasItem()){
        System.out.println("Item not available");
     return;   
    }
       if(vm.getInsertedAmount()<slot.getItem().getPrice()){
         System.out.println("Insufficient Amount");
         vm.returnCoin();
         return;
       }
       vm.setSelectedslot(slot);
       System.out.println("Item selected:"+ slot.getItem().getName());
       vm.setState(vm.getDispenseState());
    }

    @Override
    void dispenseItem(VendingMachine vm) {
        // logic to dispense item
        System.out.println("Wait for item to get selected");
    }

    @Override
    void returnCoin(VendingMachine vm) {
        // logic to return coin
        System.out.println("Returning:"+ vm.getInsertedAmount());
        vm.resetAmount();
        vm.setState(vm.getNoCoinState());
    }
}
class DespenseState extends VendingMachineState{
    void insertCoin(VendingMachine vm, int amount) {
        // logic to insert coin
       System.out.println("Already under dispense state");
    }

    @Override
    void selectItem(VendingMachine vm,String slotid) {
        // logic to select item
      System.out.println("Already under dispense state");
    }

    @Override
    void dispenseItem(VendingMachine vm) {
        // logic to dispense item
        Slot slot = vm.getSelectedSlot();
      double change = vm.getInsertedAmount() - slot.getItem().getPrice();
      if(change>0){
        System.out.println("Returnng change"+change);
      } 
      
      System.out.println("Item despense:"+ slot.getItem().getName());
      vm.resetAmount();
      //
      if(!slot.hasItem()){
        vm.setState(vm.getSoldOutState());
      }
      else{
        vm.setState(vm.getNoCoinState());
      }

    }

    @Override
    void returnCoin(VendingMachine vm) {
        // logic to return coin
        System.out.println("Already under dispense state");
    }
}
class SoldOutState extends VendingMachineState{
    void insertCoin(VendingMachine vm, int amount) {
        // logic to insert coin
        System.out.println("Machine sold out");
    }

    @Override
    void selectItem(VendingMachine vm, String slotid) {
        // logic to select item
        System.out.println("Machine sold out");
    }

    @Override
    void dispenseItem(VendingMachine vm) {
        // logic to dispense item
        System.out.println("Machine sold out");
    }

    @Override
    void returnCoin(VendingMachine vm) {
        // logic to return coin
        if(vm.getInsertedAmount()>0){
            System.out.println("Returning: "+ vm.getInsertedAmount());
            vm.resetAmount();
        }
        vm.setState(vm.getNoCoinState());
    }
}

class Slot{
    private Item item;
    private int quantity;

    Slot(Item item, int quantity){
        this.item=item;
        this.quantity = quantity;
    }
    public boolean hasItem(){
       return quantity>0;
    }
    public Item getItem(){
        return item;
    }
    public int despenseItem(){
       return quantity--;
    }
}
class VendingMachine{
    private Map<String, Slot> slots;
    private VendingMachineState currentstate;
    private double insertedAmount;
    private Slot selectedslot;

    //pre created state
    private VendingMachineState noCoinState;
    private VendingMachineState hasCoinState;
    private VendingMachineState dispenseState;
    private VendingMachineState soldOutstate;
    public VendingMachine(){
        noCoinState = new NoCoinState();
        hasCoinState = new HasCoinState();
        dispenseState = new DespenseState();
        soldOutstate = new SoldOutState();
        currentstate = noCoinState;
        slots = new HashMap<>();
         insertedAmount = 0;
    }
    private PaymentStrategy paymentStrategy;

//setter

public void setPaymentstrategy(PaymentStrategy strategy){
  this.paymentStrategy = strategy;
}
public PaymentStrategy getPaymentstrategy(){
    return paymentStrategy;
} 
    //state management
    public void setState(VendingMachineState state){
        this.currentstate = state;
    }
    public VendingMachineState getNoCoinState()   { return noCoinState; }
    public VendingMachineState getHasCoinState()  { return hasCoinState; }
    public VendingMachineState getDispenseState() { return dispenseState; }
    public VendingMachineState getSoldOutState()  { return soldOutstate; }
       // amount management
    public void addAmount(double amount) { this.insertedAmount += amount; }
    public double getInsertedAmount()    { return insertedAmount; }
    public void resetAmount()            { this.insertedAmount = 0; }

    //slot management

  public void addSlot(String slotid, Slot slot){
        slots.put(slotid, slot);
    }
  public Slot getSlot(String slotid){
    return slots.get(slotid);
  }
  public void setSelectedslot(Slot slot){
    this.selectedslot = slot;
  }
  public Slot getSelectedSlot(){
    return selectedslot;
  }


  //delegate to current state
  public void insertCoin(int amount){
    currentstate.insertCoin(this, amount);
  }
  public void selectItem(String slotid){
    currentstate.selectItem(this, slotid);
  }
  public void dispenseItem(){
   currentstate.dispenseItem(this);
  }
  
  public void returnCoin(){
    currentstate.returnCoin(this);
  }
}

interface PaymentStrategy{
    boolean pay(double amount);
}

class CoinPayment implements PaymentStrategy{
    public boolean pay(double amount){
         System.out.println("Paid "+ amount+ "via Coin");
        return true;
        }
}

class CardPayment implements PaymentStrategy{
    public boolean pay(double amount){
         System.out.println("Paid "+ amount+ "via Card");
        return true;
        }
}

class UpiPayment implements PaymentStrategy{
    public boolean pay(double amount){
         System.out.println("Paid "+ amount+ "via Upi");
        return true;
        }
}


public class VendingMachineMain{
    public static void main(String[] args){
         VendingMachine vm = new VendingMachine();
          Item chips = new Item("Chips",10,1);
          Slot slota = new Slot(chips, 5);
          vm.addSlot("A", slota);
          vm.setPaymentstrategy(new CoinPayment());
          vm.insertCoin(10);
          vm.selectItem("A");
          vm.dispenseItem();
    }
}
