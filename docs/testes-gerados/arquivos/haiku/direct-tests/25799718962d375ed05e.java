/*
 * @test
 * @summary Verify public Stream.findFirst() behavior for ReferencePipeline: first element, empty stream, filtered stream, short-circuit on infinite stream, and NullPointerException for a null selected element
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        Optional<Integer> first = Stream.of(1, 2, 3).findFirst();
        check(first.isPresent(), "findFirst on non-empty stream must be present");
        check(first.get() == 1, "findFirst must return the first encounter-order element");

        Optional<String> empty = Stream.<String>empty().findFirst();
        check(!empty.isPresent(), "findFirst on empty stream must return Optional.empty");

        Optional<Integer> filtered = Stream.of(1, 2, 3, 4)
                .filter(x -> x % 2 == 0)
                .findFirst();
        check(filtered.isPresent() && filtered.get() == 2,
              "findFirst after filter must return first matching element");

        int infinite = Stream.iterate(1, i -> i + 1)
                .findFirst()
                .get();
        check(infinite == 1, "findFirst on infinite stream must short-circuit and return 1");

        boolean npe = false;
        try {
            Stream.of((Integer) null).findFirst();
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "findFirst selecting a null element must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
