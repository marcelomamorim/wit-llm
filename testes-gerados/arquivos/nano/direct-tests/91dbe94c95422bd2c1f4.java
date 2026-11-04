/*
 * @test
 * @summary Test for ThreadLocalRandom forEachRemaining method
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        class DummyConsumer implements IntConsumer {
            int count = 0;
            int lastValue = -1;
            @Override
            public void accept(int value) {
                if (value < 0) {
                    throw new AssertionError("Value should be non-negative");
                }
                lastValue = value;
                count++;
            }
        }
        DummyConsumer consumer = new DummyConsumer();
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        // Set index to a value less than fence to trigger loop
        // Since index and fence are package-private, we simulate the scenario by calling method directly
        // and assuming index < fence (default behavior)
        rng.forEachRemaining(consumer);
        if (consumer.count == 0) {
            throw new AssertionError("Consumer was not called");
        }
        System.out.println("testForEachRemainingWithValidConsumer passed, calls: " + consumer.count);
    }

    static void testForEachRemainingWithNullConsumer() {
        try {
            ThreadLocalRandom rng = ThreadLocalRandom.current();
            rng.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testForEachRemainingWithNullConsumer passed");
        }
    }
}
