package no.njm.example;

@FunctionalInterface
interface PersonFactory<P extends Person> {
    P create(String firstName, String lastname);
}
