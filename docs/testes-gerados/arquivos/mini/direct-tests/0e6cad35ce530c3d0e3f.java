/*
 * @test
 * @summary Test IntPipeline.filter method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntPredicate;
import java.util.Objects;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterWithAlwaysTruePredicate();
        testFilterWithAlwaysFalsePredicate();
        testFilterWithMixedPredicate();
    }

    private static void testFilterWithNullPredicate() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            stream.filter(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFilterWithAlwaysTruePredicate() {
        IntStream stream = IntStream.of(1, 2, 3);
        IntStream filtered = stream.filter(new IntPredicate() {
            public boolean test(int value) {
                return true;
            }
        });
        int sum = filtered.sum();
        if (sum != 6) {
            throw new AssertionError("Filter with always true predicate should include all elements, sum= " + sum);
        }
    }

    private static void testFilterWithAlwaysFalsePredicate() {
        IntStream stream = IntStream.of(1, 2, 3);
        IntStream filtered = stream.filter(new IntPredicate() {
            public boolean test(int value) {
                return false;
            }
        });
        long count = filtered.count();
        if (count != 0) {
            throw new AssertionError("Filter with always false predicate should exclude all elements, count= " + count);
        }
    }

    private static void testFilterWithMixedPredicate() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        IntStream filtered = stream.filter(new IntPredicate() {
            public boolean test(int value) {
                return value % 2 == 0; // even numbers only
            }
        });
        int[] expected = {2, 4};
        int index = 0;
        for (int val : (Iterable<Integer>) () -> filtered.iterator()) {
            if (index >= expected.length || val != expected[index]) {
                throw new AssertionError("Filtered stream returned unexpected value " + val);
            }
            index++;
        }
        if (index != expected.length) {
            throw new AssertionError("Filtered stream returned fewer elements than expected");
        }
    }
}
