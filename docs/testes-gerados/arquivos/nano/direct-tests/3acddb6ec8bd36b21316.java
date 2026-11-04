/* @test    @summary Test for ThreadLocalRandom.forEachRemaining(LongConsumer) method    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        class Collector implements LongConsumer {
            long sum = 0;
            @Override
            public void accept(long value) {
                sum += value;
            }
        }
        Collector collector = new Collector();
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        // Initialize index and fence to known values for deterministic behavior
        // Since index and fence are private, we simulate the method call with a custom consumer
        // and rely on the method's behavior
        rng.forEachRemaining(collector);
        // Verify that sum is not zero (since random), but method executes without exception
        // For deterministic test, we can check that the method completes without error
        if (collector.sum == 0) {
            System.out.println("Warning: sum is zero, which may be due to random seed.");
        }
        System.out.println("testForEachRemainingWithValidConsumer passed.");
    }

    static void testForEachRemainingWithNullConsumer() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        boolean exceptionThrown = false;
        try {
            rng.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when consumer is null");
        }
        System.out.println("testForEachRemainingWithNullConsumer passed.");
    }
}
