package com.ab.practice;

import com.ab.model.Customer;
import com.ab.model.Department;
import com.ab.model.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FlatMap {

    public static void main(String[] args) {

        List<List<Integer>> numbers = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8)
        );

        //Using Java 8 Streams, flatten this into a single List<Integer>: [1, 2, 3, 4, 5, 6, 7, 8]

        List<Integer> flattenedList = numbers.stream()
                .flatMap(Collection::stream)
                .toList();
        System.out.println("1. Flattened List : " + flattenedList);

        //-----------------------------------------------------------------

        List<Employee> employees = Arrays.asList(
                new Employee("John", Arrays.asList("Java", "Spring")),
                new Employee("David", Arrays.asList("Python", "Django")),
                new Employee("Alex", Arrays.asList("Java", "AWS"))
        );

        // List the skills of all the employees
        List<String> listOfSkills = employees.stream()
                .flatMap(e -> e.getSkills().stream())
                .toList();
        System.out.println("List of all the skills: " + listOfSkills);

        //Get a list of unique skills from all employees.
        List<String> listOfUniqueSkills = employees.stream()
                .flatMap(e -> e.getSkills().stream())
                .distinct()
                .toList();
        System.out.println("List of Unique skills : " + listOfUniqueSkills);

        //--------------------------------------------------------------------

        List<Department> departments = Arrays.asList(
                new Department("IT", Arrays.asList(
                        new Employee("John", Arrays.asList("Java", "Spring")),
                        new Employee("Alex", Arrays.asList("Java", "AWS"))
                )),
                new Department("HR", Arrays.asList(
                        new Employee("David", Arrays.asList("Python", "Excel")),
                        new Employee("Sarah", Arrays.asList("Excel", "Communication"))
                ))
        );

        //Get a single list containing all employees from all departments.

       /* List<String> listOfAllEmployees = departments.stream()
                .map(Department::getEmployees)
                .flatMap(e -> e.stream().map(Employee::getName))
                .toList();*/
        List<String> listOfAllEmployees = departments.stream()
                .flatMap(d -> d.getEmployees().stream()
                                .map(Employee::getName))
                .toList();

        System.out.println("List of employees from all departments: " + listOfAllEmployees);

        //-----------------------------------------------------------------------------

        List<List<String>> teams = Arrays.asList(Arrays.asList("John", "Alex"),
                                                Arrays.asList("David", "Sarah"),
                                                Arrays.asList("Mike", "Chris")
                                            );
        //Find the total number of characters in all employee names combined.
        int sumOfChars = teams.stream()
                .flatMap(l -> l.stream().map(String::length))
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Sum of all the Characters: " + sumOfChars);

        //------------------------------------------------------------

        List<Customer> customers = Arrays.asList(
                new Customer("John", Arrays.asList("Laptop", "Mouse")),
                new Customer("Alex", Arrays.asList("Keyboard", "Monitor")),
                new Customer("David", Arrays.asList("Mouse", "Headphones"))
        );

        //Get a single list containing all products purchased by all customers.

        List<String> listOfProducts = customers.stream()
                .flatMap(c -> c.getProductName().stream())
                .toList();
        System.out.println("List of all purchased products: " + listOfProducts);

        //--------------------------------------

        List<List<Integer>> nums = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        //Find the sum of all even numbers.

        int sumOfEvenNums = nums.stream()
                .flatMapToInt(l -> l.stream()
                        .mapToInt(Integer::intValue))
                .filter(n -> n%2 == 0)
                .sum();
        System.out.println("Sum of Even numbers: " + sumOfEvenNums);

        List<Employee> employees2 = Arrays.asList(
                new Employee("John", Arrays.asList("Java", "Spring", "AWS")),
                new Employee("Alex", Arrays.asList("Python", "Django")),
                new Employee("David", Arrays.asList("Java", "Docker"))
        );

        // Get a list containing the length of every skill name.

        List<Integer> lengthOfString = employees2.stream()
                .flatMap(le -> le.getSkills().stream()
                        .map(String::length))
                .toList();
        System.out.println("list of length of strings: " + lengthOfString);

        List<Department> departments2 = Arrays.asList(
                new Department("IT", Arrays.asList(
                        new Employee("John", Arrays.asList("Java", "Spring")),
                        new Employee("Alex", Arrays.asList("AWS", "Docker"))
                )),
                new Department("HR", Arrays.asList(
                        new Employee("David", Arrays.asList("Excel", "Communication")),
                        new Employee("Sarah", Arrays.asList("Recruitment"))
                ))
        );

        //Get a list of all unique skills across all departments.
        List<String> listOfUniqueSkills2 = departments2.stream()
                .flatMap(d -> d.getEmployees().stream()
                        .flatMap(e -> e.getSkills().stream()))
                .distinct()
                .toList();
        System.out.println("List of unique skills: " + listOfUniqueSkills2);

        //----------------------------------------------------------

        List<List<String>> words = Arrays.asList(
                Arrays.asList("Java", "Spring"),
                Arrays.asList("Python", "Django"),
                Arrays.asList("JavaScript", "React")
        );
        //Find the longest word across all the nested lists.
        String longestString = words.stream()
                .flatMap(Collection::stream)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
//                .collect(Collectors.toMap(Function.identity(), String::length))
//                .entrySet().stream()
//                .max(Comparator.comparingInt(Map.Entry::getValue))
//                .map(Map.Entry::getKey)
//                .orElseGet(() -> null);
        System.out.println("longest String : " + longestString);

    }
}
