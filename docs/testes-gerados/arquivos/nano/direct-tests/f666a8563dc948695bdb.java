/* @test    @summary Test tryAdvance method of SplittableRandom for correct behavior    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithConsumer();
    }

    static void testTryAdvanceWithNullConsumer() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    static void testTryAdvanceWithConsumer() {
        SplittableRandom sr = new SplittableRandom(12345L);
        final int[] count = {0};
        IntConsumer consumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                count[0]++;
                // Optionally, check value range if needed
            }
        };
        boolean result = sr.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance returned false unexpectedly");
        }
        if (count[0] != 1) {
            throw new AssertionError("Consumer was not invoked exactly once");
        }
        // Call again to test behavior when index reaches fence
        // Simulate index >= fence by manipulating internal state if accessible
        // Since internal state is not accessible, we test multiple calls
        for (int i = 0; i < 100; i++) {
            sr.tryAdvance(consumer); // just to ensure no exceptions
        }
    }
}
