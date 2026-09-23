package com.ab.practice;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MediumStreams {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(101, "Alice", "IT", 120000),
                new Employee(102, "Bob", "HR", 85000),
                new Employee(103, "Charlie", "IT", 95000),
                new Employee(104, "David", "Finance", 110000),
                new Employee(105, "Eva", "HR", 92000),
                new Employee(106, "Frank", "IT", 120000),
                new Employee(107, "Grace", "Finance", 125000),
                new Employee(108, "Henry", "IT", 105000),
                new Employee(109, "Ivy", "HR", 85000),
                new Employee(110, "Jack", "Finance", 110000),
                new Employee(111, "Kate", "IT", 95000),
                new Employee(112, "Leo", "Finance", 130000)
        );

//        Find all employees whose salary is greater than 100,000.
        List<Employee> maxSalariedEmployees = employees.stream()
                .filter(n -> n.getSalary() > 100000)
                .toList();
        System.out.println("11. Maximum salaried employee: " + maxSalariedEmployees);

//        Get the names of all employees who belong to the "IT" department.
        List<String> itDeptEmployees = employees.stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase("IT"))
                .map(Employee::getName)
                .toList();
        System.out.println("12. Employees names who work under IT department: " + itDeptEmployees);

//        Find the employee with the highest salary.
        Optional<Employee> maxSalEmpl = employees.stream()
                .max(Comparator.comparing(Employee::getSalary));
        System.out.println("13. Maximum salaried employee is : " + maxSalEmpl);

        /*Optional<Double> maxSalary = employees.stream()
                .map(Employee::getSalary)
                .max(Comparator.naturalOrder());
        Optional<Employee> maxSalariedEmp = employees.stream()
                .filter(employee -> employee.getSalary() == maxSalary.orElseGet(() -> 0.0))
                .findFirst();
        System.out.println("13. Maximum salaried employee is : " + maxSalariedEmp);*/

//        Find the second-highest distinct salary.
        Optional<Double> secondHighestSal = employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();
        System.out.println("14. Second highest distinct salary is : " + secondHighestSal);


//        List<Double> sortedSalary = employees.stream()
//                .map(Employee::getSalary)
//                .distinct()
//                .sorted(Comparator.reverseOrder())
//                .toList();
//        System.out.println("14. Second highest distinct salary is : " + sortedSalary.get(1));

//        Group employees by department.
        Map<String, List<Employee>> groupByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(" 15. Employees by department: " + groupByDept);

//        Count the number of employees in each department.
        Map<String, Long> countEmpByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println("16. Number of employees for each department : " + countEmpByDept);

//        Find the average salary of employees in each department.
        Map<String, Double> avgSalInEachDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment
                        , Collectors.averagingDouble(Employee::getSalary)));
        System.out.println("17. Average Salary in Each Department: " + avgSalInEachDept);

//        Find the highest-paid employee from each department.
        Map<String, Optional<Employee>> maxSalEmpPerDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
        System.out.println("18: Highest-paid employee from each department : " + maxSalEmpPerDept);

//        Sort all employees by salary in descending order.
        List<Employee> sortedEmpSalDesc = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .toList();
        System.out.println("19: Sorted Employee salaries in descending order: " + sortedEmpSalDesc);

//        Given a list of integers, find all duplicate numbers.
        //found difficult
        List<Integer> numbers = List.of(1,1,14,3,5,454,54,2,2,2,2,3,3,4,5,6,78,9,8);
        //way1:
        Set<Integer> duplicates = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(n -> n.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        System.out.println("20. List of duplicate integers in given list: " + duplicates);
        //way2:

        Set<Integer> duplicateValues = new HashSet<>();

        List<Integer> listOfDupls = numbers.stream()
                .filter(n -> !duplicateValues.add(n))
                .distinct()
                .toList();
        System.out.println("20. List of duplicate integers in given list: " + listOfDupls);
    }
}
