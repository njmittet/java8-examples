package no.njm.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Java 8 offers a range of new functional interfaces.
 * <p>
 * Predicates are boolean-valued functions of one argument.
 * The interface contains various default methods for composing
 * predicates to complex logical terms (and, or, negate)
 */
public class Predicates {

    private static final Logger log = LoggerFactory.getLogger(Predicates.class);

    public static void main(String[] args) {
        Predicate<String> emptyString = (source) -> source.length() > 0;
        log.debug("Testing a String returns {}", emptyString.test("value"));
        log.debug("Testing an empty String returns {}", emptyString.test(""));

        // Default method negate()
        Predicate<String> negatedTest = emptyString.negate();
        log.debug("Testing a negated String returns {}", negatedTest.test("value"));

        // Combining predicates
        Predicate<Integer> above = value -> value > 2;
        Predicate<Integer> belov = value -> value < 4;
        Predicate<Integer> between = above.and(belov);
        log.debug("Testing betweein with 2 yields {}", between.test(2));
        log.debug("Testing betweein with 3 yields {}", between.test(3));
        log.debug("Testing betweein with 4 yields {}", between.test(4));

        // Using method reference
        Predicate<List<String>> emptyList = List::isEmpty;
        log.debug("Testing an empty List returns {}", emptyList.test(new ArrayList<>()));
    }
}
