/*
 * @test
 * @summary Tests LongStream.iterate null handling and basic iteration behavior
 * @run main LongStreamWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testIterateProducesSeedThenApplies();
        testIterateWithLimitProducesExpectedSequence();
    }

    static void testNullFunctionThrowsNPE() {
        try {
            LongStream.iterate(1L, null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testIterateProducesSeedThenApplies() {
        long[] actual = LongStream.iterate(1L, x -> x * 2).limit(5).toArray();
        long[] expected = {1L, 2L, 4L, 8L, 16L};
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError("Unexpected sequence: " + Arrays.toString(actual));
        }
    }

    static void testIterateWithLimitProducesExpectedSequence() {
        long sum = LongStream.iterate(0L, x -> x + 3L).limit(4).sum();
        if (sum != 18L) {
            throw new AssertionError("Unexpected sum: " + sum);
        }
    }
}
