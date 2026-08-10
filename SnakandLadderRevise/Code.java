import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Snake{
    private int head;
    private int tail;
    public Snake(int head, int tail){
        this.head = head;
        this.tail = tail;
    }
    public int getHead(){
        return head;
    }
    public int getTail(){
        return tail;
    }
}
 class Ladder{
    private int start;
    private int end;
    public Ladder(int start, int end){
        this.start = start;
        this.end = end;
    }
    public int getStart(){
        return start;
    }
    public int getEnd(){
        return end;
    }
}

class Player{
    private String name;
    private int position;

    public Player(String name, int position){
        this.name = name;
        this.position = position;
    }
    public String getName(){
        return name;
    }
    public int getPosition(){
        return position;
    }   
    public void setPosition(int position){
        this.position = position;
    }
}

interface DiceStrategy{
    int roll();
}
class SingleDice implements DiceStrategy{

    @Override
    public int roll(){
        return (int)(Math.random() * 6) + 1;
    }
}

class Board{
    private int size;
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private Map<Integer,Integer> jump;

    public Board(int size){
        this.size = size;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.jump = new HashMap<>();
    }
    public void addSnake(Snake snake){
        snakes.add(snake);
        jump.put(snake.getHead(), snake.getTail());
    }
    public void addLadder(Ladder ladder){
        ladders.add(ladder);
        jump.put(ladder.getStart(), ladder.getEnd());
    }
    public int getNewPosition(int position){
        if(jump.containsKey(position)){
            return jump.get(position);
        }
        return position;
    }
    public int getSize(){
        return size;
    }

}


class Game{
    private Board board;
    private List<Player> players;
    private DiceStrategy dice;
    private int currentPlayerIndex;
 public Game(Board board, List<Player> players, DiceStrategy dice){
        this.board = board;
        this.players = players;
        this.dice = dice;
        this.currentPlayerIndex = 0;
    }
   private boolean isWinner(Player player){
    return player.getPosition() == board.getSize();
   }
   private int getNextPlayer(){
    return (currentPlayerIndex + 1) % players.size();
   }

   public void PlayTurn(Player player){
    int roll = dice.roll();
    int newPosition = player.getPosition()+roll;

    if(newPosition > board.getSize()){
        System.out.println("Need to roll again");
        return;
    }
    //check if player hits a snake or ladder
    newPosition = board.getNewPosition(newPosition);
    player.setPosition(newPosition);

    System.out.println(player.getName() + " rolled a " + roll + " and moved to position " + newPosition);
   }

   public void start(){ 
    while(true){
        Player current = players.get(currentPlayerIndex);
        PlayTurn(current);

        if(isWinner(current)){
            System.out.println(current.getName() + " wins the game!");
            break;
        }
        currentPlayerIndex = getNextPlayer();
    }

   }
}

public class Code{

    public static void main(String[] args){

        Board board = new Board(100);

        //create players
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", 0));
        players.add(new Player("Player 2", 0));

        //add snake
        board.addSnake(new Snake(16, 6));
        board.addSnake(new Snake(47, 26));

        //add ladder
        board.addLadder(new Ladder(2, 38));
        board.addLadder(new Ladder(7, 14));

        //create dice
        DiceStrategy dice = new SingleDice();

        //create game
        Game game = new Game(board, players, dice);
        game.start();
    }
}