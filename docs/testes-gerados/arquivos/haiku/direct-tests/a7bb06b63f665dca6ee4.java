/*
 * @test
 * @summary Verify Stream.iterate(seed, hasNext, next) element sequence through forEach, which reaches Stream.forEachRemaining on the sequential spliterator
 * @run main StreamWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testProducesElementsWhilePredicateHolds() {
        List<Integer> out = new ArrayList<>();
        Stream.iterate(1, x -> x < 5, x -> x + 1).forEach(out::add);
        check(out.equals(List.of(1, 2, 3, 4)),
              "expected [1, 2, 3, 4] but was " + out);
    }

    static void testEmptyWhenSeedFailsPredicate() {
        List<Integer> out = new ArrayList<>();
        Stream.iterate(10, x -> x < 5, x -> x + 1).forEach(out::add);
        check(out.isEmpty(), "expected no elements but was " + out);
    }

    static void testSingleElementWhenNextFailsPredicate() {
        List<Integer> out = new ArrayList<>();
        Stream.iterate(3, x -> x < 4, x -> x + 1).forEach(out::add);
        check(out.equals(List.of(3)), "expected [3] but was " + out);
    }

    static void testNextAppliedToPreviousElement() {
        List<Integer> out = new ArrayList<>();
        Stream.iterate(1, x -> x <= 16, x -> x * 2).forEach(out::add);
        check(out.equals(List.of(1, 2, 4, 8, 16)),
              "expected [1, 2, 4, 8, 16] but was " + out);
    }

    static void testCountMatchesForEachSequence() {
        long count = Stream.iterate(0, x -> x < 10, x -> x + 2).count();
        check(count == 5L, "expected count 5 but was " + count);
    }

    public static void main(String[] args) {
        testProducesElementsWhilePredicateHolds();
        testEmptyWhenSeedFailsPredicate();
        testSingleElementWhenNextFailsPredicate();
        testNextAppliedToPreviousElement();
        testCountMatchesForEachSequence();
    }
}
