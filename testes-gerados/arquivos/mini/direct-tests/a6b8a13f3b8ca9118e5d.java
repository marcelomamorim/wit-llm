/*
 * @test
 * @summary Test LongStream.iterate method for deterministic behavior and null checks
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongUnaryOperator;
import java.util.Objects;
import java.util.NoSuchElementException;

public class LongStreamWitupTest {
    public static void main(String[] args) {
        testIterateBasicSequence();
        testIterateNullFunction();
        testIterateFunctionProducesSequence();
    }

    static void testIterateBasicSequence() {
        LongUnaryOperator f = x -> x + 1;
        LongStream stream = LongStream.iterate(0L, f);
        long[] firstFive = stream.limit(5).toArray();
        long[] expected = {0L, 1L, 2L, 3L, 4L};
        for (int i = 0; i < expected.length; i++) {
            if (firstFive[i] != expected[i]) {
                throw new AssertionError("Expected " + expected[i] + " but got " + firstFive[i]);
            }
        }
    }

    static void testIterateNullFunction() {
        try {
            LongStream.iterate(0L, null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testIterateFunctionProducesSequence() {
        LongUnaryOperator f = x -> x * 2;
        LongStream stream = LongStream.iterate(1L, f);
        long[] firstFour = stream.limit(4).toArray();
        long[] expected = {1L, 2L, 4L, 8L};
        for (int i = 0; i < expected.length; i++) {
            if (firstFour[i] != expected[i]) {
                throw new AssertionError("Expected " + expected[i] + " but got " + firstFour[i]);
            }
        }
    }
}
