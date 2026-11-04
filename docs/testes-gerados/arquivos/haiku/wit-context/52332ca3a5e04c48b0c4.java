/*
 * @test
 * @summary Verifies IntStream.iterate null-function NPE and sequential element generation
 * @run main IntStreamWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testDoublingSequence();
        testNegativeSeedIncrement();
        testLimitZeroProducesEmpty();
    }

    static void testNullFunctionThrowsNPE() {
        try {
            IntStream.iterate(0, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException when f is null");
    }

    static void testDoublingSequence() {
        int[] actual = IntStream.iterate(1, x -> x * 2).limit(5).toArray();
        int[] expected = {1, 2, 4, 8, 16};
        check(Arrays.equals(expected, actual), "doubling sequence mismatch: " + Arrays.toString(actual));
    }

    static void testNegativeSeedIncrement() {
        int[] actual = IntStream.iterate(-3, x -> x + 1).limit(3).toArray();
        int[] expected = {-3, -2, -1};
        check(Arrays.equals(expected, actual), "negative seed sequence mismatch: " + Arrays.toString(actual));
    }

    static void testLimitZeroProducesEmpty() {
        long count = IntStream.iterate(0, x -> x + 1).limit(0).count();
        check(count == 0L, "expected empty stream, count=" + count);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
