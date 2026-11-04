/*
 * @test
 * @summary Test SplittableRandom.longs() produces a LongStream with expected properties
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        SplittableRandom rng = new SplittableRandom(12345L);
        LongStream stream = rng.longs();

        // Test that stream is not null
        if (stream == null) {
            throw new AssertionError("longs() returned null stream");
        }

        // Test that stream produces values and they are within expected range
        // We limit to first 100 values to avoid infinite stream processing
        long[] values = stream.limit(100).toArray();

        if (values.length != 100) {
            throw new AssertionError("Expected 100 values from longs() stream, got " + values.length);
        }

        for (long v : values) {
            // Values should be >= 0 and < Long.MAX_VALUE as per spliterator bounds
            if (v < 0L || v >= Long.MAX_VALUE) {
                throw new AssertionError("Value out of expected range: " + v);
            }
        }

        // Test that multiple calls produce different streams
        LongStream stream2 = rng.longs();
        long[] values2 = stream2.limit(100).toArray();

        boolean allEqual = true;
        for (int i = 0; i < 100; i++) {
            if (values[i] != values2[i]) {
                allEqual = false;
                break;
            }
        }

        if (allEqual) {
            throw new AssertionError("Two longs() streams from same rng instance produced identical sequences");
        }
    }
}
