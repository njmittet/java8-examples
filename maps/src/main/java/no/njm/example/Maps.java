package no.njm.example;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps don't support streams, but various new and useful methods for doing common tasks are implemented.
 */
public class Maps {

    private static final Logger log = LoggerFactory.getLogger(Maps.class);

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i); // Default Method added to Map
        }
        map.forEach((id, val) -> System.out.println(val)); // BiConsumer functional interface

        // BiConsumer is expected  to operate via side-effects
        map.computeIfPresent(3, (num, val) -> val + num);
        log.debug("map.get(3) is {} ", map.get(3));

        map.computeIfPresent(9, (num, val) -> null);
        log.debug("map.get(9) is {} ", map.get(9));

        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);
        log.debug("map.containsKey(23) is {} ", map.containsKey(23));

        map.computeIfAbsent(3, num -> "bam");
        log.debug("map.get(3) is {} ", map.get(3));

        // Remove entries for a a given key works only when correct value is referenced
        map.remove(3, "val3");
        log.debug("map.get(3) is {} ", map.get(3)); // Not removed

        map.remove(3, "val33");
        log.debug("map.get(3) is {} ", map.get(3)); // Removed

        // Default values
        String defaultValue = map.getOrDefault(42, "Void");
        log.debug("defaultValue is {} ", defaultValue);

        // Merging Map entries
        map.merge(9, "val9", (a, b) -> a.concat(b)); // Initially null
        log.debug("map.get(9) is {} ", map.get(9));

        map.merge(9, "concat", (a, b) -> a.concat(b));
        log.debug("map.get(9) is {} ", map.get(9));
    }
}
