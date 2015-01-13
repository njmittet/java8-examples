package no.njm.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Collect accepts a Collector which consists of four different operations:
 * a supplier, an accumulator, a combiner and a finisher. Several collectors are implemented
 * in the Collectors class.
 */
public class Collector {

    private static final Logger log = LoggerFactory.getLogger(Collector.class);

    static List<Person> persons = Arrays.asList(
            new Person("Alfa", 20),
            new Person("Bravo", 30),
            new Person("Charlie", 40),
            new Person("Delta", 50),
            new Person("Echo", 60));

    public static void main(String[] args) {
        toList();
        count();
        average();
        join();
    }

    /**
     * Returns the Stream output as a List.
     */
    private static void toList() {
        List<Person> filtered = persons.stream()
                .filter(p -> p.name.contains("a")) // Predicate
                .collect(Collectors.toList());

        log.debug("{} of {} persons have  \"a\" int their name", filtered.size(), persons.size());
    }

    /**
     * Counting Stream elements;
     */
    private static void count() {
        long count = persons.stream()
                            .collect(Collectors.counting());

        log.debug("Count is {}", count);
    }

    /**
     * Calculating an average of the Stream output.
     */
    private static void average() {
        Double average = persons.stream()
                                .collect(Collectors.averagingInt(p -> p.age));

        log.debug("Average age is {}", average);
    }

    /**
     * Joining the Stream output.
     */
    private static void join() {
        String phrase = persons
                .stream()
                .filter(p -> p.age >= 30)
                .map(p -> p.name)
                .collect(Collectors.joining(", ")); // (String delimiter, String prefix, String postfix)

        log.debug("Joined person string is \"{}\"", phrase);
    }
}
