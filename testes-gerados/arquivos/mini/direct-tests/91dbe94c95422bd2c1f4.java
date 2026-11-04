/*
 * @test
 * @summary Test ThreadLocalRandom.forEachRemaining(IntConsumer) behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingWithConsumer();
    }

    static void testForEachRemainingNullConsumer() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        try {
            tlr.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithConsumer() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        // We cannot directly set index, fence, origin, bound fields because they are package-private and no public setter.
        // So we test the observable behavior: forEachRemaining calls consumer.accept at least once if index < fence.
        // Since we cannot set index and fence, we test that forEachRemaining does not throw and calls consumer.accept at least once.

        final int[] count = new int[1];
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                count[0]++;
                if (value < 0) {
                    throw new RuntimeException("Value should be non-negative");
                }
            }
        };

        // Call forEachRemaining multiple times to increase chance of index < fence
        for (int i = 0; i < 10; i++) {
            try {
                tlr.forEachRemaining(consumer);
            } catch (RuntimeException e) {
                throw new RuntimeException("Unexpected exception during forEachRemaining: " + e);
            }
        }

        if (count[0] == 0) {
            throw new RuntimeException("Consumer.accept was never called, possible index >= fence always");
        }
    }
}
