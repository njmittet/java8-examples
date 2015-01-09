package no.njm.example;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Comparators are well known from older versions of Java. Java 8 adds various default methods to the interface
 */
public class Comparators {

    private static final Logger log = LoggerFactory.getLogger(Functions.class);

    public static void main(String[] args) {
        Person person1 = new Person("First", "Last");
        Person person2 = new Person("Second", "Third");

        Comparator<Person> firstNameComparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
        log.debug("Comparing firstname returns {}", firstNameComparator.compare(person1, person2));
        log.debug("Comparing lastname reversed returns {}", firstNameComparator.reversed().compare(person1, person2));

        // Chaining comparators
        log.debug("Comparing firstname and lastname returns {}", firstNameComparator
                .thenComparing((p1, p2) -> p1.lastName.compareTo(p2.lastName))
                .compare(person1, person2));
    }
}
