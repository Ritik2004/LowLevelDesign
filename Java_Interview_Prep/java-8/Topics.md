
1) Default Methods - A default method is java 8 is a methods inside a interface that has an implementation. They are defined using default keyword hence called default methods.
Reason: To add new methods to existing interfaces without forcing all implementing classes to implement them.
Implementing classes can use the default implementation or override it.
Example - 
public interface Vehicle{
    default void start(){
        System.out.println("Vehicle is starting");
    }
}

2) Static method in interface
Static methods belong to the interface itself and are called using the interface name. The are used when we want the brhaviour to remain with interface not implenting classes.
Reason: To provide utility/helper methods related to the interface without requiring an implementing class to override them.
Static interface methods are not inherited by implementing classes and cannot be overridden.


3) Functional Interface - It is a interface that ontains only one abtract method(unimplemented) making it suitable to use with lambda expression and Method reference.
Condition - 
Must have one abstract method
can have multiple default methods
can have multiple static methods
have @FunctionalInterface on it(optional)

4) Lambda expression - They are used to represent funtion interface in a short and concise way. They reduce lot of boilerplate code.

5) Method Reference - It is a shorter way to write lambda expression when lambda simply calls a exisitng method. Uses :: operator

6) Types of functional interface
 Predicate - It is pre-defined functional interface that takes one argument and returns a boolean value used for conditional testing.(test) EXAMPLE - Filter 
 Consumer - It is pre-defied functional iterface that taks some input and return some output used for transforming data. (Apply) Ex - forEach
 Function - It is pre-defied functional iterface that taks one input and return nothing, used to perform operation when we dont need to return anything. Ex - map
 Supplier - It does not take any onput but return but returns a value, used when we want to generate or supply data.

7) Optional - It is a class intrdocued in java 8 that may or may not contain a value. It is manily used to avoid null ponier exception when dealing with null values. 

8) Stream it is a interface is a sequence of elements that supports various operations to process data. It allows operations like filter data , transform data, iterate data

9) Collect - It is a method inside stream interface that takes all element from stream and trnasform them into result like map,St or list.

10) Intermediate Operations - These operations transform or filter a stream and return another Stream. They are generally lazy, meaning they don't execute until a terminal operation is called. Example - filter(),map(),flatMap(),sorted(),distinct(),limit(),skip(). they come under lazy evaluation as they dont execute until terminal operation
is called.
   
   Example - 
   List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
   Stream<Integer> result = numbers.stream()
        .filter(n -> n > 2)
        .map(n -> n * 2);

  This pipleine wont return anything and is not executed as there is no termianl opeation.

11) Terminal Operations - These operations finish the stream pipeline and produce a result or perform an action. After a terminal operation, the stream cannot be reused.
Eager loading is when terminal operation is there because till it is not intermediate wont give any result. 

 Example - collect(),forEach(),count(),reduce()
 List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
 List<Integer> result = numbers.stream()
        .filter(n -> n > 2)
        .map(n -> n * 2)
        .collect(Collectors.toList());
System.out.println(result); // [6, 8, 10]
Now we cant reuse this stream again.


Exception Handling in java-  

1. Checked exceptions (compile-time)
These are checked by the compiler. You must handle them using try-catch or declare them using throws. They dont let us compile the code.
Example - IOException,SQLException,FileNotFoundException

2. Unchecked Exception (Runtime exception)
These occur at runtime and are not required to be handled or declared by the compiler.
Example - NullPointerException,ArithmeticException,ArrayIndexOutOfBoundsException

exception Hierarchy - Lok at image in Exception folder


Finally block is always executed.In all the cases/