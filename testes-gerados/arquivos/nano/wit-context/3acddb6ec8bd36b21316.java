/*
 * @test
 * @summary Test for ThreadLocalRandom.forEachRemaining(LongConsumer)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        try {
            rand.forEachRemaining(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        final long[] result = {0};
        LongConsumer consumer = new LongConsumer() {
            @Override
            public void accept(long value) {
                result[0] += value;
            }
        };
        rand.forEachRemaining(consumer);
        if (result[0] == 0) {
            System.err.println("Consumer did not process any elements");
            System.exit(1);
        }
    }
}
