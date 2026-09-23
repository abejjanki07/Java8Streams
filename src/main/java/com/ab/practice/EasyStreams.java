package com.ab.practice;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

public class EasyStreams {

    public static void main(String[] args) {

        List<Integer> integers = List.of(12,423,4,42,43,54,647,57,876,431,2,1,2,1);
        List<String> strings = List.of("sheldon","amy","penny","leonard","howie","bernie","raj","stuart");

        //    Given a list of integers, find all even numbers.
        List<Integer> evenIntegers = integers.stream()
                .filter(i -> (i % 2 == 0))
                .toList();
        System.out.println("1. Even integers from the given list are: " + evenIntegers);

        //    Given a list of integers, find all numbers greater than 50.
        List<Integer> greaterThan50 = integers.stream()
                .filter(n -> n > 50)
                .toList();
        System.out.println("2. Integers with value greater than 50 : ; " + greaterThan50);

        //    Given a list of strings, convert all strings to uppercase.
        List<String> uppercaseStrings = strings.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println("3. Converting given strings to uppercase : " + uppercaseStrings);

        //    Given a list of strings, find the first string that starts with "A".
        Optional<String> a = strings.stream()
                .filter(s -> s.substring(0,1).equalsIgnoreCase("a"))
                .findFirst();
        System.out.println("4. " + a.orElseGet(() -> "No one ") + " has name starting with A");

        //    Given a list of integers, count how many numbers are greater than 25.
        long countGreaterThan25 = integers.stream()
                .filter(n -> n > 25)
                .count();
        System.out.println("5. Count of given integers greater than 25: " + countGreaterThan25);
        //    Given a list of integers, find the maximum number.
        Optional<Integer> maxInteger = integers.stream()
                .max(Comparator.naturalOrder());
        System.out.println("6. Maximum integer from given integers: " + maxInteger.orElseGet(() -> 0));

        //    Given a list of integers, find the minimum number.
        OptionalInt minInteger = integers.stream()
                .mapToInt(Integer::intValue)
                .min();
        System.out.println("7. Minimum integer from given integers: " + minInteger.orElseGet(() -> 0));

        //    Given a list of integers, remove all duplicate numbers.
        List<Integer> distinctIntegers = integers.stream().distinct().toList();
        System.out.println("8. Distinct integers: " + distinctIntegers);

        //    Given a list of integers, sort them in descending order.
        List<Integer> sortedIntegers = integers.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
        System.out.println("9. Sorted Integers in descending order: " + sortedIntegers);

        //    Given a list of integers, calculate the sum of all numbers.
        int sumOfIntegers = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("10. sum of all given integers: " + sumOfIntegers);

    }
}
