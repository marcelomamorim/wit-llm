/*
 * @test
 * @summary Test SplittableRandom.tryAdvance(DoubleConsumer) for null argument and normal usage
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullConsumer();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullConsumer() {
        SplittableRandom rng = new SplittableRandom(12345L);
        try {
            rng.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceNormal() {
        SplittableRandom rng = new SplittableRandom(12345L);
        final double[] value = new double[1];
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double d) {
                value[0] = d;
            }
        };

        boolean advanced = rng.tryAdvance(consumer);
        if (!advanced) {
            throw new RuntimeException("tryAdvance returned false unexpectedly");
        }
        // The value should be within the default origin (0.0) and bound (1.0)
        if (value[0] < 0.0 || value[0] >= 1.0) {
            throw new RuntimeException("Returned value out of expected range: " + value[0]);
        }

        // Subsequent calls should eventually return false after many calls
        // but here we just test that multiple calls return boolean and do not throw
        for (int i = 0; i < 10; i++) {
            boolean result = rng.tryAdvance(consumer);
            if (result) {
                if (value[0] < 0.0 || value[0] >= 1.0) {
                    throw new RuntimeException("Returned value out of expected range on iteration " + i + ": " + value[0]);
                }
            }
        }
    }
}
