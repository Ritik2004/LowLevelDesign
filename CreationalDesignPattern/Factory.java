

interface Logistics{
    void send();
}

class Road implements Logistics{
    @Override
    public void send(){
       System.out.println("Road Logistics");
    }
}

class Air implements Logistics{
    @Override
    public void send(){
        System.out.println("Air Logistics");
     }
    }

class LogisticFactory{
    public static Logistics createlogistic(String mode){
        if(mode.equals("Air")){
            return new Air();
        } 
        else if(mode.equals("Road")){
            return new Road();
        }
        return null;
    }
}
public class Factory{
    public static void main(String[] args) {
        Logistics logistics = LogisticFactory.createlogistic("Air");
        logistics.send();
        Logistics logistics2 = LogisticFactory.createlogistic("Road");
        logistics2.send();
    }
}