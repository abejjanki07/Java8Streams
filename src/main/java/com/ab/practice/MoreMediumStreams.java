package com.ab.practice;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MoreMediumStreams {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 2, 5, 6, 3, 7, 1);
        // Return the numbers that occur more than once.

        //Way1:
        List<Integer> listOfDuplicates = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("Numbers that occur more than once: " + listOfDuplicates);

        //Way2:
        Set<Integer> duplicates = new HashSet<>();
        List<Integer> list = numbers.stream()
                .filter(n -> !duplicates.add(n))
                .distinct()
                .toList();
        System.out.println("Numbers that occur more than once: " + list);

        List<Integer> numbers2 = List.of(1, 2, 2, 3, 3, 3, 4, 5, 5, 5, 5, 6);
        // Return the numbers whose frequency is greater than 2.
        List<Integer> repeatedMoreThanTwice = numbers2.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 2)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("numbers whose frequency is greater than 2: " + repeatedMoreThanTwice);

        List<Integer> numbers3 = List.of(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        // Find the frequency of each number
        Map<Integer, Long> frequency = numbers3.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("frequency of each number : " + frequency);

        //Find the number with the highest frequency
        List<Integer> numbers4 = List.of(1, 2, 3, 2, 4, 2, 5, 3, 2, 1);
        Optional<Map.Entry<Integer, Long>> max = numbers4.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue));
        System.out.println(" number with the highest frequency : " + max);


        //Find duplicate employee names
        List<Employee> employees = List.of(
                new Employee(101, "Alice", "IT", 120000),
                new Employee(102, "Bob", "HR", 85000),
                new Employee(103, "Alice", "Finance", 110000),
                new Employee(104, "David", "IT", 95000),
                new Employee(105, "Bob", "HR", 92000),
                new Employee(106, "Charlie", "IT", 105000)
        );
        List<String> dupEmpNames = employees.stream()
                .collect(Collectors.groupingBy(Employee::getName, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("Duplicate employee names: " + dupEmpNames);
    }
}
