package no.njm.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sequential streams uses a single thread while parallel uses multiple threads.
 */
public class ParallelStreams {

    private static final Logger log = LoggerFactory.getLogger(Streams.class);

    private static final int MAX = 1000;

    public static void main(String[] args) {
        sortSequential();
        sortParallel();
    }

    private static void sortSequential() {
        List<String> list = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            list.add(uuid.toString());
        }

        long start = System.nanoTime();
        list.stream()
                  .sorted()
                  .count();
        long end = System.nanoTime();
        log.debug(String.format("Sequential sort took: %d ms", TimeUnit.NANOSECONDS.toMillis(end - start)));
    }

    private static void sortParallel() {
        List<String> list = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            list.add(uuid.toString());
        }

        long start = System.nanoTime();
        list.parallelStream()
                  .sorted()
                  .count();
        long end = System.nanoTime();
        log.debug(String.format("Parallel sort took: %d ms", TimeUnit.NANOSECONDS.toMillis(end - start)));
    }
}
