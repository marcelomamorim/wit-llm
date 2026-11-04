/*
 * @test
 * @summary Test java.util.Random.forEachRemaining(LongConsumer) behavior
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.function.LongConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNullConsumerThrows();
        testForEachRemainingConsumesAll();
    }

    private static void testNullConsumerThrows() {
        Random random = new Random();
        try {
            random.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingConsumesAll() {
        // We cannot access index, fence, rng, origin, bound fields directly,
        // so we test observable behavior with a subclass that exposes these.
        TestRandom r = new TestRandom(0L, 5L);
        final long[] results = new long[5];
        final int[] count = new int[1];

        r.forEachRemaining(new LongConsumer() {
            public void accept(long value) {
                if (count[0] < results.length) {
                    results[count[0]++] = value;
                } else {
                    throw new RuntimeException("Too many calls to accept");
                }
            }
        });

        if (count[0] != 5) {
            throw new RuntimeException("Expected 5 calls to accept, got " + count[0]);
        }

        // Check that values are within origin (0) and bound (5)
        for (int i = 0; i < count[0]; i++) {
            if (results[i] < 0 || results[i] >= 5) {
                throw new RuntimeException("Value out of bounds: " + results[i]);
            }
        }
    }

    // Subclass to expose protected fields and internalNextLong for testing
    static class TestRandom extends Random {
        long index;
        long fence;
        Random rng;
        long origin;
        long bound;

        TestRandom(long origin, long bound) {
            super(12345L); // seed arbitrary
            this.index = 0L;
            this.fence = 5L;
            this.rng = this;
            this.origin = origin;
            this.bound = bound;
        }

        // Expose internalNextLong as public for testing
        long internalNextLong(long origin, long bound) {
            // Use nextLong() to generate a value in [origin, bound)
            long r = nextLong();
            long n = bound - origin;
            long val = origin + Math.floorMod(r, n);
            return val;
        }

        // Override forEachRemaining to use our fields
        @Override
        public void forEachRemaining(LongConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                index = f;
                Random r = rng;
                long o = origin, b = bound;
                do {
                    consumer.accept(r.internalNextLong(o, b));
                } while (++i < f);
            }
        }
    }
}
