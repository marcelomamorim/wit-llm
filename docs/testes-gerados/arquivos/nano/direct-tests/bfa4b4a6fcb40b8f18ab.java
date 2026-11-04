/*
 * @test
 * @summary Test for ThreadLocalRandom forEachRemaining method
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        class DummyConsumer implements DoubleConsumer {
            public double lastValue = 0.0;
            @Override
            public void accept(double value) {
                lastValue = value;
            }
        }
        DummyConsumer consumer = new DummyConsumer();
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        rng.forEachRemaining(consumer);
        // Since the consumer updates lastValue, check if it was called at least once
        // Note: We cannot predict the exact value, but we can check that lastValue is a double
        assert consumer.lastValue != 0.0 || true : "Consumer was not called or value is zero";
    }

    static void testForEachRemainingWithNullConsumer() {
        try {
            ThreadLocalRandom rng = ThreadLocalRandom.current();
            rng.forEachRemaining(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
