/*
 * @test
 * @summary Test java.util.Random.RandomLongsSpliterator.tryAdvance(LongConsumer) for null consumer and normal usage
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.LongConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullConsumer();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullConsumer() {
        // Create a RandomLongsSpliterator instance via Random.longs() to access tryAdvance
        Random random = new Random(12345L);
        // longs() returns a LongStream; get its spliterator
        java.util.Spliterator.OfLong spliterator = random.longs().spliterator();

        try {
            // tryAdvance with null should throw NullPointerException
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceNormal() {
        Random random = new Random(12345L);
        java.util.Spliterator.OfLong spliterator = random.longs(5).spliterator();

        final long[] results = new long[5];
        final int[] count = {0};
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                results[count[0]++] = value;
            }
        };

        int advances = 0;
        while (spliterator.tryAdvance(consumer)) {
            advances++;
        }

        if (advances != 5) {
            throw new RuntimeException("Expected 5 advances, got " + advances);
        }

        // Check that all results are set (not zero) - since seed is fixed, values are deterministic
        for (int i = 0; i < 5; i++) {
            if (results[i] == 0L) {
                throw new RuntimeException("Result at index " + i + " is zero, unexpected");
            }
        }
    }
}
