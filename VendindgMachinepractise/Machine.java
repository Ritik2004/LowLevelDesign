package VendindgMachinepractise;

import java.util.HashMap;
import java.util.Map;

class Product{
    int id;
    String name;
    int price;

    Product(int id,String name,int price){
        this.id=id;
        this.name=name;
        this.price=price;
    }
    public int getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPrice(int price){
        this.price=price;
    }
}

class Inventory{
    Map<Product,Integer>map;
    Inventory(){
        this.map=new HashMap<>();
    }

    public int getQuantity(Product product){
         return map.getOrDefault(product, 0);
    }
    public void addQuantity(Product product, int quantity){
         map.put(product,map.getOrDefault(product,0)+quantity);
    }

    public void removeQuantity(Product product, int quantity){
           map.put(product,map.getOrDefault(product,0)-quantity);
    }
}

class Payment{
    int amount;
    Payment(int amount){
        this.amount=amount;
    }

    public int getAmout(){
        return amount;
    }
}


class VendingMachine{
    Inventory inventory;
    Product product;
    private int insertedamount;
    VendingMachinestate state;
    private PaymentStrategy paymentStrategy;
   VendingMachine(Inventory inventory){
          this.inventory=inventory;
          this.insertedamount = 0;
          this.state = new IdleState();
   }

public void setPaymentstrategy(PaymentStrategy strategy){
    this.paymentStrategy = strategy;
}

public void checkout(int amount){
    paymentStrategy.pay(amount);
}
public void setState(VendingMachinestate state) {
    this.state = state;
}

public void setProduct(Product product) {
    this.product = product;
}

public void selectProduct(Product product) {
    state.selectProduct(this, product);
}

public void insertAmount(int amount) {
    state.insertAmount(this,amount);
}

public void dispense() {
    state.dispense(this);
}

public void cancel() {
    state.cancel(this);
}
public void addInsertedAmount(int amount) {
    insertedamount += amount;
}
public Product getProduct(){
    return product;
}
public int getinsertedamount(){
    return insertedamount;
}

public Inventory getInventory(){
    return inventory;
}

public void resetTransaction() {
    insertedamount = 0;
    product = null;
}
public int calculateChange() {
    return insertedamount - product.getPrice();
}
}


interface VendingMachinestate {

    void selectProduct(VendingMachine machine, Product product);

    void insertAmount(VendingMachine machine, int amount);

    void dispense(VendingMachine machine);

    void cancel(VendingMachine machine);
}


class IdleState implements VendingMachinestate{
   public void selectProduct(VendingMachine machine, Product product) {
        machine.setProduct(product);
        System.out.println("Selected product: " + product.getName());
        machine.setState(new ProductSelectedState());
    }

    public void insertAmount(VendingMachine machine, int amount) {
        System.out.println("Please select a product first");
    }

    public void dispense(VendingMachine machine) {
        System.out.println("Please select a product first");
    }

    public void cancel(VendingMachine machine) {
        System.out.println("Nothing to cancel");
    }
    
}


class ProductSelectedState implements VendingMachinestate {

    @Override
    public void selectProduct(VendingMachine machine, Product product) {
        System.out.println("Product already selected");
    }

   @Override
public void insertAmount(VendingMachine machine, int amount) {
    machine.addInsertedAmount(amount);
    System.out.println("Money inserted: " + amount);

    machine.setState(new MoneyInsertedState());
}

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Please insert money first");
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("Transaction cancelled");
        machine.setState(new IdleState());
    }
}

class MoneyInsertedState implements VendingMachinestate {

    @Override
    public void selectProduct(VendingMachine machine, Product product) {
        System.out.println("Product already selected");
    }

    @Override
    public void insertAmount(VendingMachine machine, int amount) {
        machine.addInsertedAmount(amount);
        System.out.println("More money inserted");
    }

    @Override
    public void dispense(VendingMachine machine) {
          machine.setState(new Dispensingstate());
    machine.dispense();
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("Transaction cancelled");
        machine.setState(new IdleState());
    }
}

class Dispensingstate implements VendingMachinestate{

    @Override
    public void selectProduct(VendingMachine machine, Product product) {
       System.out.println("Already dispensing");
       
    }

    @Override
    public void insertAmount(VendingMachine machine, int amount) {
         machine.addInsertedAmount(amount);
       System.out.println("Already dispensing");
    }

    @Override
    synchronized public void dispense(VendingMachine machine) {
       Product product = machine.getProduct();
       if(machine.getInventory().getQuantity(product)<=0){
        System.out.println("Product not available");
        return;
       } 
       if (machine.getinsertedamount() < product.getPrice()) {
        System.out.println("Insufficient money");
           return; 
           }

            System.out.println("Dispensing " + product.getName());
 
    int change = machine.calculateChange();

System.out.println("Change: " + change);        

    machine.getInventory().removeQuantity(product, 1);

    machine.resetTransaction();

    machine.setState(new IdleState());
       
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("Already dispensing");
    }
    
}

interface PaymentStrategy{
    public void pay(int amount);
}

class UpiPayment implements PaymentStrategy{
    @Override
    public void pay(int amount){
            System.out.println("This is paid through upi. Amount is " + amount );
    }
}

class CashPayment implements PaymentStrategy{
    @Override
    public void pay(int amount){
            System.out.println("This is paid through Cash. Amount is " + amount );
    }
}

public class Machine {
    public static void main(String[] args){
         Product chips = new Product(1, "Chips", 20);

        // Inventory
        Inventory inventory = new Inventory();
        inventory.addQuantity(chips, 5);

          VendingMachine machine = new VendingMachine(inventory);
         machine.setPaymentstrategy(new UpiPayment());
machine.selectProduct(chips);

machine.insertAmount(50);
machine.checkout(50);
      machine.dispense();

System.out.println(
    "Remaining: " + inventory.getQuantity(chips)
);
    }
    
}
