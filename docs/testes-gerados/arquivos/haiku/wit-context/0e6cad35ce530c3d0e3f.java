/*
 * @test
 * @summary Verifies IntStream.filter behavior, including NullPointerException for a null predicate
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNullPredicateThrowsNPE();
        testEvenNumbersAreKept();
        testNoElementMatches();
        testEmptySourceYieldsEmpty();
        testAllElementsMatch();
    }

    static void testNullPredicateThrowsNPE() {
        boolean thrown = false;
        try {
            IntStream.of(1, 2, 3).filter(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "filter(null) must throw NullPointerException");
    }

    static void testEvenNumbersAreKept() {
        int[] result = IntStream.range(0, 10).filter(x -> x % 2 == 0).toArray();
        check(Arrays.equals(result, new int[] {0, 2, 4, 6, 8}),
              "even filter returned " + Arrays.toString(result));
    }

    static void testNoElementMatches() {
        int[] result = IntStream.of(1, 3, 5).filter(x -> x % 2 == 0).toArray();
        check(result.length == 0, "expected empty result but got " + Arrays.toString(result));
    }

    static void testEmptySourceYieldsEmpty() {
        int[] result = IntStream.empty().filter(x -> true).toArray();
        check(result.length == 0, "filter on empty stream must be empty");
    }

    static void testAllElementsMatch() {
        int[] result = IntStream.of(4, 5, 6).filter(x -> true).toArray();
        check(Arrays.equals(result, new int[] {4, 5, 6}),
              "all-matching filter returned " + Arrays.toString(result));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
