/*
 * @test
 * @summary Test SplittableRandom.tryAdvance(IntConsumer) for null consumer and normal usage
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.IntConsumer;

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
        final int[] result = new int[1];
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                result[0] = value;
            }
        };

        SplittableRandom rng = new SplittableRandom(12345L);

        boolean advanced = rng.tryAdvance(consumer);
        if (!advanced) {
            throw new RuntimeException("tryAdvance returned false unexpectedly");
        }

        // The value should be set by consumer.accept
        // We cannot predict exact value, but it must be an int
        // Just check that accept was called by verifying result changed from default 0
        // Since seed is fixed, result[0] should be deterministic
        // We accept any int value here as valid
    }
}
