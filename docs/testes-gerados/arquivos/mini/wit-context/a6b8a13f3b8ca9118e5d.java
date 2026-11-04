/*
 * @test
 * @summary Test LongStream.iterate(long, LongUnaryOperator) for null argument and basic iteration
 * @run main LongStreamWitupTest
 */

import java.util.Objects;
import java.util.stream.LongStream;
import java.util.function.LongUnaryOperator;

public class LongStreamWitupTest {
    public static void main(String[] args) {
        testNullFunction();
        testSimpleIteration();
    }

    private static void testNullFunction() {
        try {
            LongStream.iterate(0L, null);
            throw new Error("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testSimpleIteration() {
        final long seed = 1L;
        LongUnaryOperator f = new LongUnaryOperator() {
            public long applyAsLong(long operand) {
                return operand + 1;
            }
        };

        LongStream stream = LongStream.iterate(seed, f);
        long[] results = stream.limit(5).toArray();

        long[] expected = {1L, 2L, 3L, 4L, 5L};
        if (results.length != expected.length) {
            throw new Error("Result length mismatch");
        }
        for (int i = 0; i < expected.length; i++) {
            if (results[i] != expected[i]) {
                throw new Error("Mismatch at index " + i + ": expected " + expected[i] + ", got " + results[i]);
            }
        }
    }
}
