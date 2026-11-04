/*
 * @test
 * @summary Verifies IntPipeline.allMatch through the public IntStream API, including empty, sequential, parallel and short-circuit cases
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testEmptyStreamReturnsTrue();
        testAllElementsMatch();
        testOneElementFails();
        testShortCircuitStopsAfterFirstFailure();
        testParallelAllMatch();
        testSingleElement();
    }

    static void testEmptyStreamReturnsTrue() {
        check(IntStream.empty().allMatch(i -> false), "empty stream must return true for allMatch");
    }

    static void testAllElementsMatch() {
        check(IntStream.of(1, 2, 3).allMatch(i -> i > 0), "all positive elements must match");
    }

    static void testOneElementFails() {
        check(!IntStream.of(1, 2, 3).allMatch(i -> i > 1), "allMatch must be false when one element fails");
    }

    static void testShortCircuitStopsAfterFirstFailure() {
        int[] calls = new int[1];
        boolean result = IntStream.of(1, 2, 3, 4).allMatch(i -> {
            calls[0]++;
            return i < 2;
        });
        check(!result, "allMatch must return false when predicate fails");
        check(calls[0] == 2, "allMatch must stop after first non-matching element, calls=" + calls[0]);
    }

    static void testParallelAllMatch() {
        check(IntStream.range(0, 1000).parallel().allMatch(i -> i >= 0),
              "parallel allMatch must return true when all elements match");
        check(!IntStream.range(0, 1000).parallel().allMatch(i -> i != 500),
              "parallel allMatch must return false when one element fails");
    }

    static void testSingleElement() {
        check(IntStream.of(7).allMatch(i -> i == 7), "single matching element must return true");
        check(!IntStream.of(7).allMatch(i -> i == 8), "single non-matching element must return false");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
