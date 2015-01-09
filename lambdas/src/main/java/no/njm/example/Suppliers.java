package no.njm.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Suppliers produce a result of a given generic type. Unlike Functions, Suppliers don't accept arguments.
 */
public class Suppliers {

    private static final Logger log = LoggerFactory.getLogger(Functions.class);

    public static void main(String[] args) {
        Supplier<Person> personSupplier = Person::new;
        Person person = personSupplier.get();
        person.setId(100);
        log.debug("Person has id {}", person.getId());
    }

    private static class Person {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}