/* @test    @summary Test for Random.forEachRemaining(LongConsumer) method    @run main RandomWitupTest */

import java.util.Random;
import java.util.function.LongConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        Random rand = new Random(12345L);
        try {
            rand.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    private static void testForEachRemainingWithConsumer() {
        Random rand = new Random(12345L);
        LongConsumer consumer = new LongConsumer() {
            long sum = 0;
            @Override
            public void accept(long value) {
                sum += value;
            }
        };
        // Save initial seed state
        long seedBefore = getSeed(rand);
        rand.forEachRemaining(consumer);
        long seedAfter = getSeed(rand);
        if (seedBefore == seedAfter) {
            throw new AssertionError("Seed did not change after forEachRemaining call");
        }
        // Additional check: ensure consumer was invoked at least once
        // Since consumer sums values, check if sum is non-zero
        if (((LongConsumerWithSum) consumer).sum == 0) {
            throw new AssertionError("Consumer was not invoked or sum is zero");
        }
    }

    private static long getSeed(Random rand) {
        try {
            java.lang.reflect.Field seedField = Random.class.getDeclaredField("seed");
            seedField.setAccessible(true);
            AtomicLong seedAtomic = (AtomicLong) seedField.get(rand);
            return seedAtomic.get();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed to access seed", e);
        }
    }

    private static class LongConsumerWithSum implements LongConsumer {
        long sum = 0;
        @Override
        public void accept(long value) {
            sum += value;
        }
    }
}
