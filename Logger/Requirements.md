Requirements-
1) Different log levels are supported like Info,Warn,Debug,Error and we can be able to add more so it is extensible.
2) What shld be the output destination of message can be console, file etc
3) format in which the file log message plain text and json can be extensible
4) It shld be thread safe multiple logger shld log saftely at a time

Class Entity Enums

Enums
LogLevel: DEBUG, INFO, WARN, ERROR

----------Class---------------- 
LogMessage
Field - message,level,time
Method - getMessage(),getLevel, getTime()

-------------Interface--------------
LogStrategy
method - print(LogMessage msg)

Implementation
ConsoleStartegy
FileStrategy

------------------

LogFormatter(Strategy)
method() - format

Implementation
Plaintextformatter()
JsonFormatter()


LogHandler





UML Diagram