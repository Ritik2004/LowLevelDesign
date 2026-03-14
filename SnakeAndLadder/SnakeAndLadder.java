// 1 enum

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

enum Obstacle{
    SNAKE,
    LADDER,
    NONE
}

//Step 2 Model class

class Player{
    private String name;
    private int currentPosition;

    Player(String name){
        this.name=name;
        this.currentPosition=1;
    }
    public String getName(){
        return this.name;
    }
    public int getcurrentposition(){
        return this.currentPosition;
    }
    public void setCurrentPosition(int position){
        this.currentPosition=position;
    }
}

class Cell{
    private int position;
    private Obstacle obstacle;
    private int destination;

     public Cell(int position){
        this.position=position;
        this.obstacle=Obstacle.NONE;
        this.destination=position;// as default destination is same as position no obstacle
      }

      public boolean hasObstacle(){
        return this.obstacle != Obstacle.NONE;
      }
       public int getDestination(){
        return this.destination;
       }
       public int getPosition(){
        return this.position;
       }
       public void setObstacle(Obstacle obstacle, int destination){
        this.obstacle=obstacle;
        this.destination=destination;
       }
       public Obstacle getObstacle(){
        return this.obstacle;
       }    

}
class Dice{
    int nosofdice;
    Dice(int nosofdice){
        this.nosofdice=nosofdice;
    }
    public int roll(){
        int total=0;
        for(int i=0;i<nosofdice;i++){
            total+=(int)(Math.random()*6)+1;
        }
        return total;
    }
}

class Board{
    private Cell[] cells;
    private int size;
    public Board(int size){
        this.size=size;
        this.cells=new Cell[size+1];
        for(int i=1;i<=size;i++){
            cells[i]=new Cell(i);
        }
    }
    public void addObstacle(int position ,int destination,Obstacle obstacle){
        cells[position].setObstacle(obstacle, destination);
    }
    public int getFinalPosition(int currentposition,int dicevalue){
        int newposition = currentposition+dicevalue;
        if(newposition>size){
            System.out.println("Player goes beyond the board, stays in the same position"+ currentposition);
            return currentposition;// if player goes beyond the board then he will stay in the same position
        }
        Cell landcell = cells[newposition];
        if(landcell.hasObstacle()){
            if(landcell.getObstacle() == Obstacle.SNAKE){
                System.out.println("Player hit a snake, goes down to "+ landcell.getDestination());
            } else {
                System.out.println("Player hit a ladder, goes up to "+ landcell.getDestination());
            }
          return landcell.getDestination();
        }
        return newposition;
    }
    public int getSize(){
        return this.size;
    }
}
class Game{
        Board board;
        private Deque<Player> players;
        private Dice dice;
        public Game(int boardSize, int nosofdice){
           this.board = new Board(boardSize);
           this.dice = new Dice(nosofdice);
             this.players = new ArrayDeque<>();
           
        }
        public void addPlayer(Player player){
            players.add(player);
        }
        public void addSnake(int head,int tail){
            board.addObstacle(head, tail, Obstacle.SNAKE);
        }
        public void addLadder(int start,int end){
            board.addObstacle(start, end, Obstacle.LADDER);
        }
        public void startGame(){
            System.out.println("Game Started!");
            while(true){
                Player current = players.poll();
                int dicevalue = dice.roll();
                System.out.println(current.getName() + " rolled a " + dicevalue);
               
                int newposition = board.getFinalPosition(current.getcurrentposition(), dicevalue);
               current.setCurrentPosition(newposition);

                if(newposition == board.getSize()){
                    System.out.println(current.getName() + " wins the game!");
                    break;
                }
                players.offer(current);
            }
        }
    }
    public class SnakeAndLadder{
    public static void main(String[] args){
        Game game = new Game(100,1);
        game.addPlayer(new Player("Ritik"));
        game.addPlayer(new Player("Rahul"));
        game.addSnake(14, 7);
        game.addSnake(31, 26);
        game.addLadder(3, 22);
        game.addLadder(5, 8);
        game.startGame();
    }
}