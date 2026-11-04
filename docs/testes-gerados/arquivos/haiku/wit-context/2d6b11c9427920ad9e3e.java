/*
 * @test
 * @summary Witup-generated tests for IntPipeline.noneMatch via public IntStream API
 * @run main IntPipelineWitupTest
 */

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNoneMatchEmptyStreamReturnsTrue() {
        check(IntStream.empty().noneMatch(x -> x > 0), "empty stream should yield true");
    }

    static void testNoneMatchWithMatchReturnsFalse() {
        check(!IntStream.of(1, 2, 3).noneMatch(x -> x == 2), "match present should yield false");
    }

    static void testNoneMatchWithoutMatchReturnsTrue() {
        check(IntStream.of(1, 2, 3).noneMatch(x -> x > 10), "no match should yield true");
    }

    static void testNoneMatchShortCircuits() {
        final int[] calls = new int[1];
        IntPredicate p = x -> {
            calls[0]++;
            return x == 1;
        };
        boolean result = IntStream.of(1, 2, 3).noneMatch(p);
        check(!result, "result should be false when first element matches");
        check(calls[0] == 1, "predicate should be evaluated only once, was " + calls[0]);
    }

    static void testNoneMatchOnConsumedStreamThrows() {
        IntStream s = IntStream.of(1, 2, 3);
        s.noneMatch(x -> x > 5);
        boolean thrown = false;
        try {
            s.noneMatch(x -> x > 5);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "reusing a consumed stream should throw IllegalStateException");
    }

    static void testNoneMatchNullPredicateThrows() {
        boolean thrown = false;
        try {
            IntStream.of(1).noneMatch(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null predicate should throw NullPointerException");
    }

    public static void main(String[] args) {
        testNoneMatchEmptyStreamReturnsTrue();
        testNoneMatchWithMatchReturnsFalse();
        testNoneMatchWithoutMatchReturnsTrue();
        testNoneMatchShortCircuits();
        testNoneMatchOnConsumedStreamThrows();
        testNoneMatchNullPredicateThrows();
    }
}
