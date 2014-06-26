package no.njm.example;

/**
 * We can use arbitrary interfaces as lambda expressions as
 * long as the interface only contains one abstract method.
 * <p>
 * Since default methods are not abstract you're free to add default
 * methods to your functional interface.
 */
@FunctionalInterface
public interface Converter<F, T> {

    T convert(F from);
}
