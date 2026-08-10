package Streams;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class StreamExample {
    public static void main(String[] args){
        
        // //list to stream
        //  List<Integer>list = Arrays.asList(1,2,3,4,5);
        //  Stream<Integer> mystream = list.stream();
        
        //  //array to stream
        //  String[] array = {"A","B","C"};
        //  Stream<String> stream = Arrays.stream(array);

        // //To directly create a stream
        //  Stream.of(1,2,3,4,5); 
             
        //  //it wil crete infinte stream
        //  Stream.iterate(0,x->x+1);


        //   List<Integer>list = Arrays.asList(2,6,3,4,5,6,7,8,9,8,9,2,4);
        //   List<Integer> mystream = list.stream()
        //   .filter(x -> x % 2 == 0)
        //   .map(x -> x/2).distinct()
        //   .sorted((a,b)->(b-a))
        //   .limit(3)
         
        //   .collect(Collectors.toList());
        //   System.out.println(mystream);
        //   List<Integer> mappedlist = mystream.stream().map(x -> x/2).collect(Collectors.toList());
        //   System.out.println(mappedlist);
        // List<Integer> list = Arrays.asList(2,6,3,4,5,6,7,8,9,8,9,2,4);
     
        // 1)Find the duplicate elements in the list and print them

        // List<Integer> list = Arrays.asList(1,2,3,3,4,4,2,5);
        // Set<Integer> set = new HashSet<>();
        // list.stream().filter(x->!set.add(x))
        // .distinct()
        // .collect(Collectors.toList());
        // System.out.println(set);

          
        // 2) Find the frequency of each element in the list and print them

        // List<Integer> list = Arrays.asList(1,2,3,3,4,4,2,5);
        // Map<Integer,Long> result = list.stream().collect(Collectors.groupingBy(x->x, Collectors.counting()));
        // System.out.println(result);

        // String word = "banana";
        // Map<Character, Long> result = word.chars().mapToObj(c->(char)c).collect(Collectors.groupingBy(x->x,Collectors.counting()));
        // System.out.println(result);

        // 3) Second Highest Number
        // List<Integer> list = Arrays.asList(1,2,3,4,5); 
        // Integer num = list.stream().sorted((a,b)->(b-a)).skip(1).findFirst().get();
        // System.out.println(num);

        // 4) Top 3 Highest Numbers
        // List<Integer> list = Arrays.asList(1,2,3,4,5);
        // List<Integer>result = list.stream().sorted((a,b)->(b-a)).limit(3).collect(Collectors.toList());
        // System.out.println(result);

        // 5) Highest Salary Employee
        class Employee{
            String name;
            int salary;
            public Employee(String name,int salary){
                this.name = name;
                this.salary = salary;
            }
        }
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 50000),
            new Employee("Bob", 60000),
            new Employee("Charlie", 55000)
        );
        Employee highest = employees.stream().max(Comparator.comparingInt(e->e.salary)).get();
        System.out.println("Highest Salary Employee: " + highest.name + " with salary " + highest.salary);

    }
}
