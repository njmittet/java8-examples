package no.njm.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Each lambda corresponds to a given type, specified by an interface.
 * A functional interface must contain exactly one abstract method declaration.
 */
public class Lambdas {

    private static final Logger log = LoggerFactory.getLogger(Lambdas.class);

    public static void main(String[] args) {
        Lambdas lambdas = new Lambdas();
        lambdas.basicLambda();
        lambdas.functionalInterface();
        lambdas.methodReference();
    }

    private void basicLambda() {
        List<String> list = new ArrayList<>();

        // Implementing interface Comparator
        list.sort((String a, String b) -> {
            return a.compareTo(b);
        });

        // Skipping {} and "return" for one-line methods
        list.sort((String a, String b) -> a.compareTo(b));

        // Skipping parameter types
        list.sort((a, b) -> a.compareTo(b));
    }

    private void functionalInterface() {
        // Omitting () around single input parameter
        Converter<String, Integer> converter = from -> Integer.valueOf(from);
        log.debug("Converted to {}", converter.convert("100"));
    }

    private void methodReference() {
        // Passing references to methods or constructors using the :: keyword
        Converter<String, Integer> intConverter = Integer::valueOf;
        log.debug("Converted to {}", intConverter.convert("100"));

        // Referencing instance method
        Word word = new Word();

        Converter<String, String> firstLetter = word::firstLetter;
        log.debug("First letter is {}", firstLetter.convert("string"));
    }
}
