package com.ab.model.student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    public static List<Student> getStudentList(){
        ArrayList<Student> students = new ArrayList<Student>();

        Student sheldon = new Student(1,"Sheldon",1234,22);
        Student amy = new Student(2, "Amy", 2345,22);
        Student leonard = new Student(3, "Leonard", 2345,27);
        Student penny = new Student(4, "Penny", 2345,27);
        Student howard = new Student(5, "Howard", 2345,25);
        Student bernie = new Student(6, "Bernie", 2346,27);
        Student raj = new Student(7, "Raj", 2456,27);
        Student stuart = new Student(8, "Stuart", 3456,32);

        students.add(sheldon);
        students.add(amy);
        students.add(leonard);
        students.add(penny);
        students.add(howard);
        students.add(bernie);
        students.add(raj);
        students.add(stuart);
        return students;

    }

    public static void main(String[] args) {
        //print the first 2 students Name whose age is greater than 25

        List<Student> studentList = getStudentList();
        studentList.stream()
                .filter(n -> n.getAge() > 25)
                .limit(2)
                .forEach( n -> System.out.println(n.getStudentName()));
//        Student studentAgeGreaterThan25 = optionalStudentAgeGreaterThan25.orElseGet(Student::new);
//        System.out.println(studentAgeGreaterThan25 + " has age > 25");

    }



}
