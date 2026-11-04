/*
 * @test
 * @summary Deterministic tests for java.util.SplittableRandom.longs()
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testLongsStreamBasic();
    }

    private static void testLongsStreamBasic() {
        SplittableRandom sr = new SplittableRandom(12345L);
        LongStream stream = sr.longs();
        // The stream should be infinite, so limit to 10 elements
        long[] values = stream.limit(10).toArray();

        if (values.length != 10) {
            throw new AssertionError("Expected 10 elements from longs() stream, got " + values.length);
        }

        // Check that values are within full long range (no explicit bounds, so any long is valid)
        for (long v : values) {
            // No exception expected, just sanity check that values are long
            // No specific range check since longs() produces any long
        }

        // Check that two streams from same seed produce same first 10 values
        SplittableRandom sr2 = new SplittableRandom(12345L);
        long[] values2 = sr2.longs().limit(10).toArray();

        for (int i = 0; i < 10; i++) {
            if (values[i] != values2[i]) {
                throw new AssertionError("Streams from same seed differ at index " + i + ": " + values[i] + " vs " + values2[i]);
            }
        }
    }
}
