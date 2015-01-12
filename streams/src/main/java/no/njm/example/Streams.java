package no.njm.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java.util.Stream represents a sequence of elements on which one or more operations can be performed.
 * Collections in Java 8 are extended so you can simply create streams either by calling Collection.stream().
 */
public class Streams {

    private static final Logger log = LoggerFactory.getLogger(Streams.class);

    private static List<String> largeList1;
    private static List<String> largeList2;

    static {
        int max = 1000000;
        List<String> list1 = new ArrayList<>(max);
        List<String> list2 = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            list1.add(uuid.toString());
        }
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            list2.add(uuid.toString());
        }
        largeList1 = list1;
        largeList2 = list2;
    }

    private static class Person {

        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    static List<Person> persons = Arrays.asList(
            new Person("Alfa", 20),
            new Person("Bravo", 30),
            new Person("Charlie", 40),
            new Person("Delta", 50),
            new Person("Echo", 60));

    public static void main(String[] args) {
        filter();
        sort();
        match();
        count();
        map();
        reduce();
        collector();
        toList();
        parallel();
    }

    /**
     * Filter accepts a Predicate functional interface to filter all elements of the stream.
     */
    private static void filter() {
        List<String> list = Arrays.asList("alfa", "bravo", "charlie");
        list.stream()
            .filter((s) -> s.startsWith("a"))
            .forEach(log::debug); // Consumer

        list.stream()
            .filter((s) -> s.contains("a"))
            .map(String::toUpperCase)
            .sorted()
            .forEach(log::debug);
    }

    /**
     * The elements are sorted in natural order unless you pass a custom Comparator.
     * Keep in mind that sorted does only create a sorted view of the stream.
     * The ordering of stringCollection is untouched.
     */
    private static void sort() {
        List<String> list = Arrays.asList("alfa", "bravo", "charlie");
        list.stream()
            .sorted()
            .filter((s) -> s.startsWith("a"))
            .forEach(log::debug);
    }

    /**
     * Various matching operations can be used to check whether a certain predicate matches the stream.
     */
    private static void match() {
        // True if one element matches the Predicate
        boolean oneMatches = persons.stream()
                                    .anyMatch((p) -> p.name.startsWith("A"));
        log.debug("anyMatch is {}", oneMatches);

        // True if all elements matches the Predicate
        boolean allMatches = persons.stream()
                                    .allMatch((p) -> p.name.startsWith("A"));
        log.debug("allMatch is {}", allMatches);

        // True if no element matches the Predicate
        boolean noneMatches = persons.stream()
                                     .noneMatch((p) -> p.name.startsWith("A"));
        log.debug("noneMatches is {}", noneMatches);
    }

    /**
     * Count is a terminal operation returning the number of elements in the stream as a long.
     */
    private static void count() {
        long count = persons.stream().count();
        log.debug("Count is {}", count);
    }

    /**
     * The intermediate operation map converts each element into another object via the given function.
     */
    private static void map() {
        List<String> lowerCases = Arrays.asList("alfa", "bravo", "charlie");
        List<String> upperCases = lowerCases.stream()
                                            .map(String::toUpperCase)
                                            .collect(Collectors.toList());
        upperCases.forEach(log::debug);
    }

    /**
     * This terminal operation performs a reduction on the elements of the stream with the given function.
     * The result is an Optional holding the reduced value.
     */
    private static void reduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        Optional<Integer> reduced = numbers.stream()
                                           .sorted()
                                           .reduce((n1, n2) -> n1 + n2); // Accumulator
        log.debug("Reduced numbers is {}", reduced.orElse(0));
    }

    /**
     * Convert a Map to a List.
     */
    private static void toList() {
        Map<Integer, Person> map = new HashMap<>();
        map.put(1, persons.get(0));
        map.put(2, persons.get(1));
        map.put(3, persons.get(2));

        // Manual
        List<Person> list = new ArrayList<>(map.size());
        map.entrySet()
           .stream()
           .forEach(entry -> list.add(entry.getValue()));
        log.debug("List has {} elements", list.size());
    }

    /**
     * Collect accepts a Collector which consists of four different operations:
     * a supplier, an accumulator, a combiner and a finisher. Several collectors are implemented
     * in the Collectors class.
     */
    private static void collector() {
        // To List
        List<Person> filtered = persons.stream()
                .filter(p -> p.name.contains("a")) // Predicate
                .collect(Collectors.toList());
        log.debug("{} of {} persons have  \"a\" int their name", filtered.size(), persons.size());

        // Counting
        long count = persons.stream()
                            .collect(Collectors.counting());
        log.debug("Count is {}", count);

        // Averaging
        Double average = persons.stream()
                                .collect(Collectors.averagingInt(p -> p.age));
        log.debug("Average age is {}", average);

        // Joining
        String phrase = persons
                .stream()
                .filter(p -> p.age >= 30)
                .map(p -> p.name)
                .collect(Collectors.joining(", ")); // (String delimiter, String prefix, String postfix)
        log.debug("Joined person string is \"{}\"", phrase);
    }

    /**
     * Operations on sequential streams are performed on a single thread while
     * operations on parallel streams are performed concurrent on multiple threads.
     */
    private static void parallel() {
        // Sequential sort
        long s1 = System.nanoTime();
        largeList1.stream()
                  .sorted()
                  .count();
        long e1 = System.nanoTime();
        log.debug(String.format("Sequential sort took: %d ms", TimeUnit.NANOSECONDS.toMillis(e1 - s1)));

        // Parallel sort
        long s2 = System.nanoTime();
        largeList2.parallelStream()
                  .sorted()
                  .count();
        long e2 = System.nanoTime();
        log.debug(String.format("Parallel sort took: %d ms", TimeUnit.NANOSECONDS.toMillis(e2 - s2)));
    }
}
