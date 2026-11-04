/*
 * @test
 * @summary Behavioral tests for IntStream.anyMatch (IntPipeline) through the public API, including reuse of a consumed stream
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testMatchingElementReturnsTrue() {
        check(IntStream.of(1, 2, 3).anyMatch(x -> x == 2), "expected true when an element matches");
    }

    static void testNoMatchReturnsFalse() {
        check(!IntStream.of(1, 3, 5).anyMatch(x -> x % 2 == 0), "expected false when no element matches");
    }

    static void testEmptyStreamReturnsFalse() {
        check(!IntStream.empty().anyMatch(x -> true), "expected false for empty stream");
    }

    static void testShortCircuitStopsAfterFirstMatch() {
        int[] calls = new int[1];
        boolean result = IntStream.of(1, 2, 3, 4).anyMatch(x -> {
            calls[0]++;
            return x == 2;
        });
        check(result, "expected true");
        check(calls[0] == 2, "expected predicate to be invoked exactly 2 times, was " + calls[0]);
    }

    static void testReuseOfConsumedStreamThrows() {
        IntStream s = IntStream.of(1, 2);
        s.anyMatch(x -> x == 1);
        boolean thrown = false;
        try {
            s.anyMatch(x -> x == 1);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException when reusing a consumed stream");
    }

    public static void main(String[] args) {
        testMatchingElementReturnsTrue();
        testNoMatchReturnsFalse();
        testEmptyStreamReturnsFalse();
        testShortCircuitStopsAfterFirstMatch();
        testReuseOfConsumedStreamThrows();
    }
}
