
import java.security.Timestamp;
import java.time.LocalDateTime;

enum LogLevel{
    WARN,
    INFO,
    ERROR,
    DEBUG
}


//strategy to use differet ways to log message
interface LogStrategy{
    void Print(LogMessage message);
}
class ConsoleStrategy implements LogStrategy{
   private formatStrategy formatter;
    public ConsoleStrategy(formatStrategy formatter){
        this.formatter = formatter;
    }
    @Override
    public void Print(LogMessage message){
       
        System.out.println("Console :" + formatter.format(message));
    }
}
class PdfStrategy implements LogStrategy{
    private formatStrategy formatter;
    public PdfStrategy(formatStrategy formatter){
        this.formatter = formatter;
    }
    @Override
    public void Print(LogMessage message){
        System.out.println("PDF :" + formatter.format(message));
    }
}


//strategy to use different wys to format messge

interface formatStrategy{
    String format(LogMessage message);
}
class plaintext implements formatStrategy{
    @Override
   public String format(LogMessage message){
        return "["+ message.getLevel() + "] "
        + "This log came around " + message.getTime() 
        + " - " + message.getMessage();
    }
}
class jsonformat implements formatStrategy{
    @Override
    public String format(LogMessage message){
        return  "{\"level\":\"" + message.getLevel() + 
                 "\", \"time\":\"" + message.getTime() + 
               "\",\"message\":\"" + message.getMessage() + "\"}";
    }
}


//model
class LogMessage {
       String message;
       LogLevel level;
       LocalDateTime time;

       LogMessage(String message,LogLevel level){
        this.message = message;
        this.level = level;
        this.time = LocalDateTime.now();
       }
     public String getMessage(){
        return message;
     }
     public LogLevel getLevel(){
        return level;
     }
    
     public LocalDateTime getTime(){
        return time;
     }
    }

class Logger{
    private static Logger instance = null;
    private LogStrategy strategy;
    private LogHandler handler;
    public void setHandler(LogHandler handler){
       this.handler=handler;
    }
    public void setStrategy(LogStrategy strategy){
        this.strategy=strategy;
    }
    private Logger(){
          this.strategy = new ConsoleStrategy(new plaintext()); // default
    };
        public static Logger getInstance(){
            if (instance == null){
                instance = new Logger();
            }
            return instance;
        }
    public void log(String message,LogLevel level){
       LogMessage logMessage = new LogMessage(message, level);
      
    // strategy.Print(logMessage);
    if(handler != null){
        handler.handle(logMessage);
    }
    }
    public void info(String message){
        log(message,LogLevel.INFO);
    }
    public void warn(String message){
        log(message, LogLevel.WARN);
    }
    public void error(String message){
         log(message,LogLevel.ERROR);
    }
    public void debug(String message){
        log(message,LogLevel.DEBUG);
    }
}
abstract class LogHandler{
    protected LogHandler next;

    public void setNext(LogHandler next){
        this.next=next;
    }
    public abstract void handle(LogMessage Message);
}

class DebugHandler extends LogHandler{
    private LogStrategy strategy;
    public DebugHandler(LogStrategy strategy){
        this.strategy = strategy;
    }
    @Override
    public void handle(LogMessage message){
        if(message.getLevel() == LogLevel.DEBUG){
           strategy.Print(message);
        }
        else if(next!=null){
            next.handle(message);
        }
    }
}

class WarnHandler extends LogHandler{
     private LogStrategy strategy;
    public WarnHandler(LogStrategy strategy){
        this.strategy = strategy;
    }
    @Override
    public void handle(LogMessage message){
        if(message.getLevel() == LogLevel.WARN){
          strategy.Print(message);
        }
        else if(next!=null){
            next.handle(message);
        }
    }
}

class ErrorHandler extends LogHandler{
      private LogStrategy strategy;
    public ErrorHandler(LogStrategy strategy){
        this.strategy = strategy;
    }
    @Override
    public void handle(LogMessage message){
        if(message.getLevel() == LogLevel.ERROR){
          strategy.Print(message);
        }
        else if(next!=null){
            next.handle(message);
        }
    }
}

class InfoHandler extends LogHandler{
      private LogStrategy strategy;
    public InfoHandler(LogStrategy strategy){
        this.strategy = strategy;
    }
    @Override
    public void handle(LogMessage message){
        if(message.getLevel() == LogLevel.INFO){
           strategy.Print(message);
        }
        else if(next!=null){
            next.handle(message);
        }
    }
}

public class Log{
    public static void main(String[] args){

        Logger logger = Logger.getInstance();

        //set strategy first 

        LogHandler h1 = new DebugHandler(new PdfStrategy(new jsonformat()));
        LogHandler h2 = new WarnHandler(new ConsoleStrategy(new plaintext()));
        LogHandler h3 = new InfoHandler(new PdfStrategy(new jsonformat()));
        LogHandler h4 = new ErrorHandler(new ConsoleStrategy(new plaintext()));
        h1.setNext(h2);
        h2.setNext(h3);
        h3.setNext(h4);
      
        logger.setHandler(h1);

        logger.info("Your transaction is successful");
        logger.error("Your connection to database was lost.");
    }
}
