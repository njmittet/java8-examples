package no.njm.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

/**
 * Each lambda corresponds to a given type, specified by an interface.
 * A functional interface must contain exactly one abstract method declaration.
 */
public class Lambdas {

    private static final Logger log = LoggerFactory.getLogger(Lambdas.class);
    private static int staticNum = 0;
    private int instanceNum = 0;

    public static void main(String[] args) {
        basicLambda();
        functionalInterface();
        methodReference();
        constructorReference();

        Lambdas lambdas = new Lambdas();
        lambdas.lambdaScope();
    }

    private static void basicLambda() {
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

    private static void functionalInterface() {
        // Omitting () around single input parameter
        Converter<String, Integer> stringConverter = from -> Integer.valueOf(from);
        log.debug("Converted to {}", stringConverter.convert("100"));
    }

    /**
     * Method references enables referencing an existing method by name instead
     * of using a lambda to call that method.
     */
    private static void methodReference() {
        // Passing references to methods or constructors using the :: keyword
        Converter<String, Integer> intConverter = Integer::valueOf;
        log.debug("Converted to {}", intConverter.convert("100"));

        // Referencing instance method
        WordUtil wordUtil = new WordUtil();

        Converter<String, String> firstLetter = wordUtil::firstLetter;
        log.debug("First letter is {}", firstLetter.convert("string"));
    }

    /**
     * ArrayList::new equals () -> new ArrayList<>().
     * <p>
     * Methods and constructors can be overloaded so ArrayList::new could refer to any of its three
     * constructors. The method it resolves to depends on which functional interface it's being used for.
     */
    private static void constructorReference() {
        // The compiler chooses the right constructor by matching the function interface signature
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("First", "Last");
        log.debug("Person has name {} {}", person.firstName, person.lastName);

        // The functional interface Supplier the method get() that returns an object
        HashSet<String> collection = initCollection(HashSet::new, new String[] {"First", "Second"});
        for (String element : collection) {
            log.debug("Collection element is {}", element);
        }
    }

    private static <T, P extends Collection<T>> P initCollection(Supplier<P> collectionFactory, T[] elements) {
        P collection = collectionFactory.get();
        collection.addAll(Arrays.asList(elements));
        return collection;
    }

    /**
     * Lambdas has access to (implicitly) final variables, instance fields and
     * static variables from the local outer scope.
     */
    private void lambdaScope() {
        final int i = 1;
        Converter<Integer, String> stringConverter = from -> String.valueOf(i + from);
        log.debug("Converted to {}", stringConverter.convert(3));

        // Implicitly final variable
        int j = 2;
        Converter<Integer, String> implicitConverter = from -> String.valueOf(j + from);
        log.debug("Converted to {}", implicitConverter.convert(3));

        // As in anonymous objects, lambdas can write instance fields and static variables.
        Converter<Integer, String> instanceConverter = (from) -> {
            instanceNum = 2;
            staticNum = 3;
            return String.valueOf(instanceNum + staticNum + from);
        };
        log.debug("Converted to {}", instanceConverter.convert(3));
        log.debug("instanceNum is {}", instanceNum);
        log.debug("staticNum is {}", staticNum);
    }
}
