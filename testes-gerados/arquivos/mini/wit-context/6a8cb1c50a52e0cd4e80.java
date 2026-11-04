/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.tryAdvance(IntConsumer)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWhenExhausted();
    }

    private static void testTryAdvanceWithNullConsumer() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try {
            random.tryAdvance(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        final int[] result = new int[1];
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                result[0] = value;
            }
        };

        boolean advanced = random.tryAdvance(consumer);
        if (!advanced) {
            throw new Error("tryAdvance should return true when elements remain");
        }
        // The accepted value should be within the range of int (always true) and observable
        // We cannot assert exact value, but we can assert that accept was called
        // by checking that result[0] was set (default 0 is valid, so no strict check)
    }

    private static void testTryAdvanceWhenExhausted() {
        // We simulate exhaustion by creating a spliterator with index == fence
        // but ThreadLocalRandom.tryAdvance does not expose index/fence, so we cannot
        // directly test exhaustion on ThreadLocalRandom instance.
        // Instead, we test that tryAdvance returns false if no elements remain by
        // using a custom IntConsumer and calling tryAdvance repeatedly until it returns false.

        ThreadLocalRandom random = ThreadLocalRandom.current();
        final int maxAttempts = 1000; // arbitrary large number to avoid infinite loop
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                // no-op
            }
        };

        boolean advanced = true;
        int count = 0;
        while (advanced && count < maxAttempts) {
            advanced = random.tryAdvance(consumer);
            count++;
        }
        // After exhaustion, tryAdvance should return false
        if (advanced) {
            throw new Error("tryAdvance did not return false after exhaustion");
        }
    }
}
