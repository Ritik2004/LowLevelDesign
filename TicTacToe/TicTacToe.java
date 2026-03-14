import java.util.*;

//Step 1
enum Symbol{
    X, O, EMPTY
}

//Step 2 Model Class
class Cell{
    private int row;
    private int col;
    private Symbol symbol;

    public Cell(int row,int col){
        this.row=row;
        this.col=col;
        symbol = Symbol.EMPTY;
    }
    public boolean isEmpty(){
        return this.symbol == Symbol.EMPTY;
    }
    public void setSymbol(Symbol symbol){
        this.symbol = symbol;
    }
         public Symbol getSymbol(){
        return this.symbol;
         }
}

class Player{ 
        String name;
        Symbol symbol; 
         Player(String name,Symbol symbol){
            this.name=name;
            this.symbol=symbol;
         }
    public String getName(){
              return this.name;
         }
    public Symbol getSymbol(){
        return this.symbol;
    }
}

//Step 3 Board

class Board{
    private Cell[][] grid;
    private int size;

    public Board(int size){
        this.size = size;
        this.grid = new Cell[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                grid[i][j] = new Cell(i, j);
            }
        }
    }
    public boolean isCellEmpty(int row,int col){
        return grid[row][col].isEmpty();
    }

    public boolean isValidMove(int row ,int col){
        return row>=0 && row<size && col>=0 && col<size && grid[row][col].isEmpty();
        }
    public void placeSymbol(int row,int col, Symbol symbol){
        if(isValidMove(row,col)){
            grid[row][col].setSymbol(symbol);
        }
    }

    public boolean checkWinner(Symbol symbol){
        //check rows
        for(int i=0;i<size;i++){
            boolean win = true;
            for(int j=0;j<size;j++){
                if(grid[i][j].getSymbol()!=symbol){
                    win=false;
                    break;
                }
            }
            if(win) return true;
        }
        //check columns
        for(int j=0;j<size;j++){
            boolean win = true;
            for(int i=0;i<size;i++){
                if(grid[i][j].getSymbol()!=symbol){
                    win=false;
                    break;
                }
            }
            if(win) return true;
        }
      //top left - right
      boolean win = true;
      for(int i=0;i<size;i++){
        if(grid[i][i].getSymbol()!=symbol){
            win=false;
            break;
        }
      }
        if(win) return true;

        //top right - left
        win = true;
        for(int i=0;i<size;i++){
            if(grid[i][size-i-1].getSymbol()!=symbol){
                win=false;
                break;
             }
            }
            return win;
    }
    public boolean isDraw(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(grid[i][j].isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }
   public void displayBoard(){
        System.out.println("-------------");
        for(int i=0;i<size;i++){
            System.out.print("| ");
            for(int j=0;j<size;j++){
                Symbol s = grid[i][j].getSymbol();
                if(s == Symbol.EMPTY){
                    System.out.print("- | ");
                } else {
                    System.out.print(s + " | ");
                }
            }
            System.out.println();
            System.out.println("-------------");
        }
        System.out.flush();
    }
}

//Step 4 Game Class
class Game{
    
    private Board board;
    private List<Player> players;
    private int currenindex;
    private boolean gameOver;

    public Game(int boardsize){
        this.board = new Board(boardsize);
        this.players = new ArrayList<>();
        this.currenindex=0;
        this.gameOver=false;
    }
   public void addPlayer(Player player){
     players.add(player);
   }
     public Player getCurrentPlayer(){
        return players.get(currenindex);
     }
      public void switchTurn(){
          currenindex = (currenindex + 1) % players.size();
      }     
      public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic Tac Toe!");
        board.displayBoard(); 
        while(!gameOver){ 
         
            Player currentPlayer = getCurrentPlayer();
          System.out.println(currentPlayer.getName()+ " is playing (" + currentPlayer.getSymbol() + ")");
          System.out.print("Enter row (0 - 2) : ");
          System.out.flush();
          int row = scanner.nextInt();
            System.out.print("Enter col (0 - 2) : ");
            System.out.flush();
            int col = scanner.nextInt();
            
            if(!board.isValidMove(row, col)){
                System.out.println("Invalid move. Try again.");
                continue;
            }
            //place symbol
            board.placeSymbol(row,col,currentPlayer.getSymbol());
                  System.out.println();
            System.out.println("Current Board:");
            board.displayBoard();
            System.out.println();
          //check winner
          if(board.checkWinner(currentPlayer.getSymbol())){
                board.displayBoard();
             System.out.println(currentPlayer.getName() + " wins!");
             gameOver = true;
             break;
          }
          //check draw
          if(board.isDraw()){
            board.displayBoard();
            System.out.println("It's a draw!");

            gameOver = true;
            break;
          }
          switchTurn();
        }
        scanner.close();
      }

}

public class TicTacToe{
    public static void main(String[] args){
        Game game = new Game(3);
        Player player1 = new Player("Ritik", Symbol.X);
        Player player2 = new Player("Aditi", Symbol.O);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.start();
    }
}