package no.njm.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Predicates are boolean-valued functions of one argument.
 * This built-in functional interface contains various default methods for composing
 * predicates to complex logical terms (and, or, negate).
 */
public class Predicates {

    private static final Logger log = LoggerFactory.getLogger(Predicates.class);

    public static void main(String[] args) {
        Predicate<String> emptyString = (string) -> string.length() > 0;
        log.debug("Testing a String yields {}", emptyString.test("value"));
        log.debug("Testing an empty String yields {}", emptyString.test(""));

        // Negating
        Predicate<String> negatedTest = emptyString.negate();
        log.debug("Testing a negated String yields {}", negatedTest.test("value"));

        // Combining predicates
        Predicate<Integer> above = value -> value >= 2;
        Predicate<Integer> belov = value -> value <= 4;
        Predicate<Integer> between2and4 = above.and(belov);

        log.debug("Testing between2and4 using 1 yields {}", between2and4.test(1));
        log.debug("Testing between2and4 using 2 yields {}", between2and4.test(2));
        log.debug("Testing between2and4 using 3 yields {}", between2and4.test(3));
        log.debug("Testing between2and4 using 4 yields {}", between2and4.test(4));
        log.debug("Testing between2and4 using 5 yields {}", between2and4.test(5));

        // Using method references
        Predicate<List<String>> emptyList = List::isEmpty;
        log.debug("Testing an empty List returns {}", emptyList.test(new ArrayList<>()));

        Predicate<Boolean> isNull = Objects::isNull;
        log.debug("Testing isNull using null yields {}", isNull.test(null));
    }
}
