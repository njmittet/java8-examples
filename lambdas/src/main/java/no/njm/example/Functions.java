package no.njm.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Functions accept one argument and produce a result.
 * Default methods can be used to chain multiple functions together (compose, andThen).
 */
public class Functions {

    private static final Logger log = LoggerFactory.getLogger(Functions.class);

    public static void main(String[] args) {
        Function<String, Integer> toInt = Integer::valueOf;
        Function<String, String> backToString1 = toInt.andThen(String::valueOf);
        log.debug("Integer converted back to string is {}", backToString1.apply("123"));

        // Compose executes passed-in function first
        Function<Integer, String> toString = String::valueOf;
        Function<String, String> backToString2 = toString.compose(Integer::valueOf);
        log.debug("Integer converted back to string is {}", backToString2.apply("123"));
    }
}