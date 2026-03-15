interface PaymentStrategy{
   public void Pay(int amount);
}

class Upipayment implements PaymentStrategy{
    @Override
   public void Pay(int amount){
        System.out.println("This is paid through upi. Amount is " + amount );
    }
}

class CreditPayment implements PaymentStrategy{
    @Override
   public void Pay(int amount){
     System.out.println("This is paid through Card.Amount is " + amount );
    }   
}
class ShoppingStartegy{
    private PaymentStrategy paymentstrategy;
    public void setPaymentstrategy(PaymentStrategy strategy){
        this.paymentstrategy = strategy;
    }
   public void checkout(int amount){
        paymentstrategy.Pay(amount);
   }
}
class StrategyExample2{
    public static void main(String[] args){
        ShoppingStartegy shoppingstartegy = new ShoppingStartegy();
        shoppingstartegy.setPaymentstrategy(new CreditPayment());
        shoppingstartegy.checkout(500);

        shoppingstartegy.setPaymentstrategy(new Upipayment());
        shoppingstartegy.checkout(5000);
    }
}
