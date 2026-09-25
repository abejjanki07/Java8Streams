# Java 8 Stream API — Technical Handbook & Interview Reference

## 📚 Table of Contents

1. [What is Stream API?](#1-what-is-stream-api)
2. [Stream Pipeline](#2-stream-pipeline)
3. [Stream is NOT a Collection](#3-stream-is-not-a-collection)
4. [Creating Streams](#4-creating-streams)
5. [`Stream.generate()`](#5-streamgenerate)
6. [Functional Interfaces Used with Streams](#6-functional-interfaces-used-with-streams)
7. [Lambda Expressions](#7-lambda-expressions)
8. [Method Chaining](#8-method-chaining)
9. [Declarative vs Imperative Programming](#9-declarative-vs-imperative-programming)
10. [`filter()`](#10-filter)
11. [Predicate](#11-predicate)
12. [`map()`](#12-map)
13. Function](#13-function)
14. [`filter()` + `map()`](#14-filter--map)
15. [Intermediate Operations](#15-intermediate-operations)
16. [Terminal Operations](#16-terminal-operations)
17. [Intermediate vs Terminal Operations](#17-intermediate-vs-terminal-operations)
18. [Lazy Evaluation](#18-lazy-evaluation)
19. [How Stream Execution Flows](#19-how-stream-execution-flows)
20. [`peek()`](#20-peek)
21. [`collect()`](#21-collect)
22. [`count()`](#22-count)
23. [`limit()`](#23-limit)
24. [`limit()` with Infinite Streams](#24-limit-with-infinite-streams)
25. [Stream Can Be Consumed Only Once](#25-stream-can-be-consumed-only-once)
26. [Complete Practical Example](#26-complete-practical-example)
27. [Same Example Using `collect()`](#27-same-example-using-collect)
28. [Stream API Mental Model](#28-stream-api-mental-model)
29. [Functional Interface Cheat Sheet](#29-functional-interface-cheat-sheet)
30. [Most Important Stream Operations](#30-most-important-stream-operations)
31. [Interview Quick Revision](#31-interview-quick-revision)
32. [One-Minute Cheat Sheet](#32-one-minute-cheat-sheet)
33. [`flatMap()` — Interview Definition](#33-flatmap--interview-definition)
34. [`map()` vs `flatMap()` — Interview Answer](#34-map-vs-flatmap--interview-answer)

## 1. What is Stream API?

Java 8 Stream API provides a way to process data from collections and other sources using a **declarative, functional-style approach**.

Instead of explicitly controlling **how** data is processed using loops, we describe **what** operation we want to perform.

### Traditional approach

```java
for (Student student : students) {
    if (student.getAge() > 25) {
        System.out.println(student.getStudentName());
    }
}
```

### Stream approach

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(student -> student.getStudentName())
        .forEach(System.out::println);
```

---

# 2. Stream Pipeline

A Stream pipeline generally consists of:

```text
Source → Intermediate Operations → Terminal Operation
```

Example:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(Student::getStudentName)
        .forEach(System.out::println);
```

### Breakdown

```text
students
   ↓
stream()
   ↓
filter()
   ↓
map()
   ↓
forEach()
```

- **Source** → where data comes from
- **Intermediate operations** → process/transform the data
- **Terminal operation** → produces the final result or performs an action

---

# 3. Stream is NOT a Collection

A common interview question:

> Is Stream a data structure like List or Set?

**No.**

A Collection is used to **store/manage data**, while a Stream is used to **process data**.

| Collection | Stream |
|---|---|
| Stores data | Processes data |
| Can be traversed multiple times | Stream is consumed after terminal operation |
| Represents data | Represents a pipeline of operations |
| Example: `List`, `Set` | Example: `Stream<T>` |

Example:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

Stream<Integer> stream = numbers.stream();
```

The `List` stores the numbers.

The `Stream` processes them.

---

# 4. Creating Streams

## From a Collection

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

Stream<Integer> stream = numbers.stream();
```

## Using `Stream.of()`

```java
Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
```

Example:

```java
Stream<String> stream =
        Stream.of("Java", "Python", "C++");
```

---

# 5. `Stream.generate()`

`Stream.generate()` creates a stream using a `Supplier`.

```java
Stream.generate(() -> "Hello")
      .limit(5)
      .forEach(System.out::println);
```

Output:

```text
Hello
Hello
Hello
Hello
Hello
```

`generate()` can create an **infinite stream**, so a limiting operation such as `limit()` is commonly used.

Example:

```java
Stream.generate(() -> Math.random())
      .limit(5)
      .forEach(System.out::println);
```

---

# 6. Functional Interfaces Used with Streams

Several Java functional interfaces are heavily used with Stream API.

| Functional Interface | Method | Input | Output | Common Stream Usage |
|---|---|---|---|---|
| `Supplier<T>` | `T get()` | None | `T` | `generate()` |
| `Consumer<T>` | `void accept(T)` | `T` | None | `forEach()`, `peek()` |
| `Predicate<T>` | `boolean test(T)` | `T` | `boolean` | `filter()` |
| `Function<T,R>` | `R apply(T)` | `T` | `R` | `map()` |

### Easy way to remember

```text
Supplier  → Supplies data
Consumer  → Consumes data
Predicate → Tests data
Function  → Transforms data
```

---

# 7. Lambda Expressions

Streams commonly use lambda expressions.

Example:

```java
numbers.forEach(number -> System.out.println(number));
```

Instead of:

```java
numbers.forEach(new Consumer<Integer>() {
    @Override
    public void accept(Integer number) {
        System.out.println(number);
    }
});
```

Lambda expressions make functional-style programming shorter and easier to read.

---

# 8. Method Chaining

Stream operations are commonly chained together.

Example:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(student -> student.getStudentName())
        .forEach(name -> System.out.println(name));
```

Each intermediate operation returns another stream, allowing the next operation to be chained.

---

# 9. Declarative vs Imperative Programming

### Imperative

Focuses on **how** to perform the task.

```java
for (Student student : students) {
    if (student.getAge() > 25) {
        System.out.println(student.getStudentName());
    }
}
```

### Declarative

Focuses on **what** should happen.

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(Student::getStudentName)
        .forEach(System.out::println);
```

Stream API encourages a declarative style.

---

# 10. `filter()`

`filter()` is used to select elements that satisfy a condition.

```java
numbers.stream()
       .filter(number -> number > 3)
       .forEach(System.out::println);
```

For:

```text
1 2 3 4 5
```

Output:

```text
4
5
```

The condition passed to `filter()` is represented by a `Predicate`.

---

# 11. Predicate

`Predicate<T>` represents a condition.

Its main method is:

```java
boolean test(T t)
```

Example:

```java
Predicate<Integer> predicate = number -> number > 3;
```

Using it:

```java
numbers.stream()
       .filter(predicate)
       .forEach(System.out::println);
```

### Remember

```text
Predicate → takes input → returns true/false
```

---

# 12. `map()`

`map()` is used to **transform each element** into another value.

Example:

```java
List<String> names =
        Arrays.asList("John", "David", "Alex");

names.stream()
     .map(name -> name.toUpperCase())
     .forEach(System.out::println);
```

Output:

```text
JOHN
DAVID
ALEX
```

`map()` uses a `Function`.

---

# 13. Function

`Function<T, R>` represents a transformation.

Its main method is:

```java
R apply(T t)
```

Example:

```java
Function<String, Integer> lengthFunction =
        name -> name.length();
```

Usage:

```java
names.stream()
     .map(lengthFunction)
     .forEach(System.out::println);
```

### Remember

```text
Function → takes T → returns R
```

---

# 14. `filter()` + `map()`

One of the most common Stream patterns:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(student -> student.getStudentName())
        .forEach(System.out::println);
```

Meaning:

```text
Student objects
      ↓
Keep students whose age > 25
      ↓
Convert Student → String name
      ↓
Print names
```

This pattern is extremely common in interviews.

---

# 15. Intermediate Operations

Intermediate operations process the stream and return another `Stream`.

Examples:

```text
filter()
map()
peek()
limit()
```

Example:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(Student::getStudentName)
        .limit(2);
```

At this point, no terminal operation has been called.

The pipeline has been built but has not been consumed.

---

# 16. Terminal Operations

A terminal operation ends the Stream pipeline.

Examples:

```text
forEach()
collect()
count()
```

Example:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .forEach(System.out::println);
```

`forEach()` is the terminal operation.

---

# 17. Intermediate vs Terminal Operations

| Intermediate | Terminal |
|---|---|
| Returns another Stream | Produces final result/action |
| Can be chained | Ends the pipeline |
| Lazy | Triggers execution |
| `filter()` | `forEach()` |
| `map()` | `collect()` |
| `peek()` | `count()` |
| `limit()` | — |

### Key interview point

```text
Intermediate operations build the pipeline.
Terminal operation triggers the pipeline.
```

---

# 18. Lazy Evaluation

Stream intermediate operations are **lazy**.

Consider:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(Student::getStudentName);
```

Nothing is actually processed yet because there is no terminal operation.

Execution begins when a terminal operation is added:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(Student::getStudentName)
        .forEach(System.out::println);
```

### Important

```text
stream()
   ↓
filter()
   ↓
map()
```

These operations build the pipeline.

```text
forEach()
```

triggers execution.

---

# 19. How Stream Execution Flows

Consider:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(Student::getStudentName)
        .forEach(System.out::println);
```

Conceptually, each element flows through the pipeline:

```text
Student
   ↓
filter()
   ↓
map()
   ↓
forEach()
```

If a student fails the `filter()`, that element does not continue to `map()`.

This is an important concept for understanding Stream execution.

---

# 20. `peek()`

`peek()` is an intermediate operation primarily useful for observing elements as they move through the pipeline, especially for debugging.

Example:

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .peek(student -> System.out.println(student))
        .map(Student::getStudentName)
        .forEach(System.out::println);
```

`peek()` uses a `Consumer`.

Important:

```text
peek() → intermediate operation
forEach() → terminal operation
```

---

# 21. `collect()`

`collect()` is a terminal operation used to gather stream results into a collection or another result structure.

Example:

```java
List<String> names =
        students.stream()
                .filter(student -> student.getAge() > 25)
                .map(Student::getStudentName)
                .collect(Collectors.toList());
```

Now the result is stored in a `List`.

### Difference

```java
.forEach(System.out::println);
```

prints/consumes the values.

```java
.collect(Collectors.toList());
```

stores the values in a `List`.

---

# 22. `count()`

`count()` is a terminal operation used to count elements.

```java
long count =
        students.stream()
                .filter(student -> student.getAge() > 25)
                .count();
```

Return type:

```java
long
```

Example:

```java
long result = numbers.stream()
                    .filter(number -> number > 3)
                    .count();
```

---

# 23. `limit()`

`limit(n)` restricts the stream to at most `n` elements.

Example:

```java
numbers.stream()
       .limit(3)
       .forEach(System.out::println);
```

For:

```text
1 2 3 4 5
```

Output:

```text
1
2
3
```

`limit()` is an intermediate operation and is short-circuiting.

It is particularly useful with potentially infinite streams.

---

# 24. `limit()` with Infinite Streams

Example:

```java
Stream.generate(() -> "Hello")
      .limit(5)
      .forEach(System.out::println);
```

Without `limit()`:

```java
Stream.generate(() -> "Hello")
      .forEach(System.out::println);
```

the stream can continue indefinitely.

---

# 25. Stream Can Be Consumed Only Once

A Stream should not be reused after a terminal operation.

Example:

```java
Stream<Integer> stream =
        Stream.of(1, 2, 3, 4, 5);

stream.forEach(System.out::println);

stream.forEach(System.out::println);
```

The second operation causes:

```text
IllegalStateException
```

### Correct approach

Create a new stream from the source:

```java
numbers.stream().forEach(System.out::println);

numbers.stream().count();
```

The collection can be used again to create a new Stream.

---

# 26. Complete Practical Example

Suppose we have:

```java
List<Student> students = ...;
```

We want:

> Find students older than 25, get their names, take only the first two, and print them.

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(student -> student.getStudentName())
        .limit(2)
        .forEach(name -> System.out.println(name));
```

### Pipeline

```text
students
   ↓
stream()
   ↓
filter(age > 25)
   ↓
map(Student → name)
   ↓
limit(2)
   ↓
forEach(print)
```

---

# 27. Same Example Using `collect()`

If the names need to be stored instead of directly printed:

```java
List<String> names =
        students.stream()
                .filter(student -> student.getAge() > 25)
                .map(Student::getStudentName)
                .limit(2)
                .collect(Collectors.toList());
```

Now:

```java
names
```

contains the resulting student names.

---

# 28. Stream API Mental Model

When solving a Stream problem, think in this order:

### Step 1 — Identify the source

```java
students.stream()
```

### Step 2 — What should be removed?

Use:

```java
filter()
```

### Step 3 — What should be transformed?

Use:

```java
map()
```

### Step 4 — Do I need to restrict the result?

Use:

```java
limit()
```

### Step 5 — What should happen with the result?

Use:

```java
forEach()
```

or

```java
collect()
```

or

```java
count()
```

---

# 29. Functional Interface Cheat Sheet

```text
Supplier
   ↓
No input → produces value
   ↓
generate()

Consumer
   ↓
Takes value → performs action
   ↓
forEach(), peek()

Predicate
   ↓
Takes value → true/false
   ↓
filter()

Function
   ↓
Takes value → transforms to another value
   ↓
map()
```

---

# 30. Most Important Stream Operations

| Operation | Purpose | Type |
|---|---|---|
| `stream()` | Create stream from collection | Stream creation |
| `of()` | Create stream from values | Stream creation |
| `generate()` | Generate values using Supplier | Stream creation |
| `filter()` | Select elements | Intermediate |
| `map()` | Transform elements | Intermediate |
| `peek()` | Observe elements | Intermediate |
| `limit()` | Restrict number of elements | Intermediate |
| `forEach()` | Perform action on elements | Terminal |
| `collect()` | Gather results | Terminal |
| `count()` | Count elements | Terminal |

---

# 31. Interview Quick Revision

### What is Stream API?

A Java 8 API for processing data in a declarative/functional style.

### Is Stream a Collection?

No. A collection stores data; a stream processes data.

### What is a Stream pipeline?

```text
Source → Intermediate Operations → Terminal Operation
```

### What is `filter()`?

Used to select elements based on a condition.

```java
.filter(x -> x > 10)
```

Uses:

```java
Predicate<T>
```

### What is `map()`?

Used to transform elements.

```java
.map(x -> x * 2)
```

Uses:

```java
Function<T, R>
```

### What is `forEach()`?

A terminal operation used to perform an action on each element.

Uses:

```java
Consumer<T>
```

### What is `collect()`?

A terminal operation used to gather stream results.

Example:

```java
.collect(Collectors.toList())
```

### What is `count()`?

A terminal operation that returns the number of elements as `long`.

### What is `peek()`?

An intermediate operation primarily used to observe elements, commonly for debugging.

Uses:

```java
Consumer<T>
```

### What is `limit()`?

An intermediate, short-circuiting operation that restricts the number of elements processed/emitted.

### What is lazy evaluation?

Intermediate operations are not executed immediately. Execution begins when a terminal operation is invoked.

### Can a Stream be reused?

No. Once a terminal operation consumes a Stream, that Stream object cannot be reused.

### What happens if you reuse it?

Typically:

```text
IllegalStateException
```

---

# 32. One-Minute Cheat Sheet

```text
Stream API
    ↓
Source
    ↓
Intermediate Operations
    ↓
Terminal Operation
```

### Intermediate

```text
filter() → select
map()    → transform
peek()   → observe/debug
limit()  → restrict
```

### Terminal

```text
forEach() → consume/action
collect() → gather result
count()   → count elements
```

### Functional Interfaces

```text
Supplier  → generate()
Consumer  → forEach(), peek()
Predicate → filter()
Function  → map()
```

### Core concepts

```text
Stream ≠ Collection
Streams are lazy
Intermediate operations build the pipeline
Terminal operation triggers execution
A Stream can be consumed only once
Stream.generate() can create an infinite stream
limit() can restrict an infinite stream
```

### Most common interview pattern

```java
students.stream()
        .filter(student -> student.getAge() > 25)
        .map(Student::getStudentName)
        .limit(2)
        .collect(Collectors.toList());
```

Think:

```text
FILTER → TRANSFORM → LIMIT → COLLECT
```

## 33. What is `flatMap()`?

> "`flatMap()` is a Stream API intermediate operation used when we have nested data, such as a list of lists or an object containing a list, and we want to flatten that nested structure into a single stream. It essentially combines mapping and flattening into one operation."

### Example

```java
List<List<Integer>> numbers = Arrays.asList(
        Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5, 6)
);

List<Integer> result = numbers.stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
```

Here:

```text
Stream<List<Integer>>
        ↓
    flatMap()
        ↓
Stream<Integer>
```

### Short version

If the interviewer wants a quick answer:

> "`flatMap()` is used to flatten nested streams into a single stream. It is useful when one element can contain multiple values, such as a list of skills inside an Employee."

---

## 34. What is the difference between `map()` and `flatMap()`?

> "`map()` is mainly used for transformation, where each input element is converted into one output element. `flatMap()` is used when an input element can produce multiple elements, and it also flattens those nested results into a single stream."

### Example of `map()`

```java
List<String> names = Arrays.asList("John", "Alex");

List<Integer> lengths = names.stream()
        .map(String::length)
        .collect(Collectors.toList());
```

Here:

```text
String → Integer
   1       1
```

So:

```text
map() → 1 input → 1 output
```

### Example of `flatMap()`

```java
List<List<Integer>> numbers = Arrays.asList(
        Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5, 6)
);

List<Integer> result = numbers.stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
```

Here:

```text
List<Integer> → multiple Integers
```

So:

```text
flatMap() → 1 input → multiple outputs → flattened
```

### One-line interview answer

> "`map()` transforms one element into another element, whereas `flatMap()` transforms and flattens nested elements into a single stream."