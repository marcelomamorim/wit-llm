/*
 * @test
 * @summary Test tryAdvance method of ThreadLocalRandom for correct behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexReachesFence();
    }

    static void testTryAdvanceWithValidConsumer() {
        class Counter implements LongConsumer {
            long count = 0;
            long lastValue = 0;
            @Override
            public void accept(long value) {
                lastValue = value;
                count++;
            }
        }
        Counter counter = new Counter();
        ThreadLocalRandom.random = null; // Reset seed if needed
        // Simulate multiple calls
        for (int i = 0; i < 10; i++) {
            boolean result = ThreadLocalRandom.current().tryAdvance(counter);
            if (i < ThreadLocalRandom.index && ThreadLocalRandom.index < ThreadLocalRandom.fence) {
                assert result : "Expected true when index < fence";
                assert counter.count == i + 1 : "Counter should increment";
            } else {
                assert !result : "Expected false when index >= fence";
            }
        }
    }

    static void testTryAdvanceWithNullConsumer() {
        boolean threw = false;
        try {
            ThreadLocalRandom.current().tryAdvance(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        assert threw : "NullPointerException should be thrown for null consumer";
    }

    static void testTryAdvanceWhenIndexReachesFence() {
        // Force index to be >= fence
        ThreadLocalRandom.index = ThreadLocalRandom.fence;
        boolean result = ThreadLocalRandom.current().tryAdvance(value -> {});
        assert !result : "Should return false when index >= fence";
    }
}
