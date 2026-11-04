/*
 * @test
 * @summary Deterministic tests for java.util.Random.longs()
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testLongsStreamProducesValues();
    }

    private static void testLongsStreamProducesValues() {
        Random rnd = new Random(12345L);
        LongStream stream = rnd.longs();
        // The stream is infinite, so limit to first 5 values
        long[] values = stream.limit(5).toArray();

        // Check that values are deterministic and within full long range
        for (int i = 0; i < values.length; i++) {
            long v = values[i];
            // Values should be within full long range (no exception expected)
            // Just check that values are not all zero (very unlikely)
            if (v == 0L) {
                throw new AssertionError("Unexpected zero value at index " + i);
            }
        }

        // Check that two Random instances with same seed produce same first value
        Random rnd2 = new Random(12345L);
        long firstValue1 = values[0];
        long firstValue2 = rnd2.longs().findFirst().getAsLong();
        if (firstValue1 != firstValue2) {
            throw new AssertionError("Different first values for same seed: " + firstValue1 + " vs " + firstValue2);
        }
    }
}
