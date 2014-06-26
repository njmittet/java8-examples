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

    private void sortList() {
        List<String> list = new ArrayList<>();

        // Verbose Lambda
        list.sort((String a, String b) -> {
            return a.compareTo(b);
        });

        // Skipping {} and "return" for one-line methods
        list.sort((String a, String b) -> a.compareTo(b));

        // Skipping parameter types
        list.sort((a, b) -> a.compareTo(b));
    }

    public static void main(String[] args) {
        Lambdas lambdas = new Lambdas();
        lambdas.sortList();
    }
}
