
import java.security.Timestamp;
import java.time.LocalDateTime;

enum LogLevel{
    WARN,
    INFO,
    ERROR,
    DEBUG
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
    private Logger(){};
        public static Logger getInstance(){
            if (instance == null){
                instance = new Logger();
            }
            return instance;
        }
    public void log(String message,LogLevel level){
       LogMessage logMessage = new LogMessage(message, level);
        System.out.println("[" + logMessage.getLevel() + "] " + logMessage.getTime() + " - "
    + logMessage.getMessage());
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

public class Log{
    public static void main(String[] args){
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        logger1.info("Your transaction is successful");
        logger2.debug("Your transaction failed here");
        System.out.println(logger1 == logger2);
    }
}
