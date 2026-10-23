import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors; 
import java.util.*;
// 1) Given list of employee print name of eomplyee whose department is Tech

public class Stream {

       private static class Employee {
          private final int id;
          private final String name;
          private final String department;

          private Employee(int id, String name, String department) {
               this.id = id;
               this.name = name;
               this.department = department;
          }
      }
     public static void main(String[] args){
        List<Employee> employees = Arrays.asList(
    new Employee(1, "Amit", "Tech"),
    new Employee(2, "Priya", "HR"),
    new Employee(3, "Rahul", "Tech"),
    new Employee(4, "Neha", "Finance"),
    new Employee(5, "Ritik", "Tech"),
    new Employee(6, "Sneha", "HR"),
    new Employee(7, "Karan", "Finance"),
    new Employee(8, "Anjali", "Tech")
);

//   List<String> names =   employees.stream()
//            .filter((emp)->emp.department.equals("Tech"))
//            .map((emp)->emp.name)
//         //    .forEach((n)->System.out.println(n));
//         .collect(Collectors.toList());
      




// 2) Convert list to map
    //     Map<Integer,String> employee = employees.stream()
    //     .collect(Collectors.toMap(
    //         (emp)->emp.id,
    //         (emp)->emp.name
    //     ));
    // System.out.println(employee);
}
}
