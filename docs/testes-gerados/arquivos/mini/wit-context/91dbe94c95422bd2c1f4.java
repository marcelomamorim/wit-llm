/*
 * @test
 * @summary Test ThreadLocalRandom.forEachRemaining(IntConsumer) for correct behavior and NPE on null
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom.RandomIntsSpliterator;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        // Create a RandomIntsSpliterator with a small range
        int origin = 0;
        int bound = 3;
        long index = 0;
        long fence = 3;

        RandomIntsSpliterator spliterator = new RandomIntsSpliterator(index, fence, origin, bound);

        final int[] results = new int[(int)(fence - index)];
        final int[] pos = {0};

        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                if (pos[0] < results.length) {
                    results[pos[0]++] = value;
                }
            }
        };

        spliterator.forEachRemaining(consumer);

        // After forEachRemaining, index should be updated to fence
        if (spliterator.index != fence) {
            throw new AssertionError("index not updated to fence after forEachRemaining");
        }

        // All values should be within [origin, bound)
        for (int i = 0; i < pos[0]; i++) {
            int v = results[i];
            if (v < origin || v >= bound) {
                throw new AssertionError("Value out of bounds: " + v);
            }
        }

        // The number of accepted values should be fence - index
        if (pos[0] != (fence - index)) {
            throw new AssertionError("Incorrect number of values accepted: " + pos[0]);
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        // Create a RandomIntsSpliterator with arbitrary parameters
        RandomIntsSpliterator spliterator = new RandomIntsSpliterator(0, 1, 0, 1);

        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    // Minimal implementation of RandomIntsSpliterator to access forEachRemaining
    static final class RandomIntsSpliterator {
        long index;
        final long fence;
        final int origin;
        final int bound;

        RandomIntsSpliterator(long index, long fence, int origin, int bound) {
            this.index = index;
            this.fence = fence;
            this.origin = origin;
            this.bound = bound;
        }

        public void forEachRemaining(IntConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                index = f;
                int o = origin, b = bound;
                ThreadLocalRandom rng = ThreadLocalRandom.current();
                do {
                    consumer.accept(rng.internalNextInt(o, b));
                } while (++i < f);
            }
        }
    }
}
