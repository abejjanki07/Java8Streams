package com.ab.practice;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HardStreams {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(101, "Alice",   "IT",      120000),
                new Employee(102, "Bob",     "HR",       85000),
                new Employee(103, "Charlie", "IT",       95000),
                new Employee(104, "David",   "Finance", 110000),
                new Employee(105, "Eva",     "HR",       92000),
                new Employee(106, "Frank",   "IT",      120000),
                new Employee(107, "Grace",   "Finance", 125000),
                new Employee(108, "Henry",   "IT",      105000),
                new Employee(109, "Ivy",     "HR",       85000),
                new Employee(110, "Jack",    "Finance", 110000),
                new Employee(111, "Kate",    "IT",       95000),
                new Employee(112, "Leo",     "Finance", 130000),
                new Employee(113, "Mia",     "HR",      100000),
                new Employee(114, "Noah",    "IT",      115000),
                new Employee(115, "Olivia",  "Finance", 105000)
        );

//        Find the second-highest-paid employee from each department.
        Map<String, Employee>  secondHighestPaidEmpInEachDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.collectingAndThen(Collectors.toList(),
                                l -> {
                                    Double secHighSal = l.stream()
                                            .map(Employee::getSalary)
                                            .distinct()
                                            .sorted(Comparator.reverseOrder())
                                            .skip(1)
                                            .findFirst().orElseGet(() -> null);
                                    return l.stream()
                                            .filter(e -> e.getSalary() == secHighSal)
                                            .findFirst()
                                            .orElseGet(() -> null);
                                }
                                )
                ));

        System.out.println("21. Second highest paid employee in each department: " +secondHighestPaidEmpInEachDept);


//        Find the department that has the highest average salary.
        Optional<Map.Entry<String, Double>> highAvgSal =
                employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue));

        System.out.println("22. Department with high avg salary: " + highAvgSal);

    }

}
