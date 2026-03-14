package VendingMachine;

class Item{
    private String name;
    private int price;
    private int quantity;

    public Item(String name, int price, int quantity){
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }
    public String getName(){
        return this.name;
    }
    public int getPrice(){
        return this.price;
    }
    public int getQuantity(){
        return this.quantity;
    }
}

abstract class  VendingMachineState{
   abstract void insertCoin(VendingMachine vm, int amount);
   abstract void selectItem(VendingMachine vm);
    abstract void dispenseItem(VendingMachine vm);
    abstract void returnCoin(VendingMachine vm);
}

class NoCoinState extends VendingMachineState{
    @Override
    void insertCoin(VendingMachine vm, int amount) {
        // logic to insert coin
        int 
    }

    @Override
    void selectItem(VendingMachine vm) {
        // logic to select item
    }

    @Override
    void dispenseItem(VendingMachine vm) {
        // logic to dispense item
    }

    @Override
    void returnCoin(VendingMachine vm) {
        // logic to return coin
    }
} 

class VendingMachineMain{

}

class VendingMachine{

}