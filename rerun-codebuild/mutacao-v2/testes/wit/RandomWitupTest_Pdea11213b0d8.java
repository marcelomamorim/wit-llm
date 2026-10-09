/*
 * @test
 * @summary Deterministic tests for java.util.Random.ints() method
 * @run main RandomWitupTest_Pdea11213b0d8
 */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest_Pdea11213b0d8 {
    public static void main(String[] args) {
        testIntsStreamProducesValues();
        testIntsStreamIsInfinite();
    }

    private static void testIntsStreamProducesValues() {
        Random rnd = new Random(12345L);
        IntStream stream = rnd.ints();
        int[] values = stream.limit(5).toArray();

        // Check that the stream produces exactly 5 values
        if (values.length != 5) {
            throw new AssertionError("Expected 5 values, got " + values.length);
        }

        // Check that values are within int range (always true, but sanity check)
        for (int v : values) {
            // No exception expected, just ensure values are int
            if (v < Integer.MIN_VALUE || v > Integer.MAX_VALUE) {
                throw new AssertionError("Value out of int range: " + v);
            }
        }

        // Check deterministic output for seed 12345L
        int[] expected = { -1157408321, -723955400, 1033096058, 1934973493, 1438108650 };
        for (int i = 0; i < expected.length; i++) {
            if (values[i] != expected[i]) {
                throw new AssertionError("Value mismatch at index " + i + ": expected " + expected[i] + ", got " + values[i]);
            }
        }
    }

    private static void testIntsStreamIsInfinite() {
        Random rnd = new Random(0L);
        IntStream stream = rnd.ints();

        // Try to get a large number of values without hanging or exception
        int limit = 10000;
        int count = 0;
        try {
            for (int v : (Iterable<Integer>) () -> stream.iterator()) {
                count++;
                if (count >= limit) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new AssertionError("Exception thrown during infinite stream iteration", e);
        }

        if (count != limit) {
            throw new AssertionError("Expected to iterate " + limit + " values, but iterated " + count);
        }
    }
}
