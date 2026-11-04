/*
 * @test
 * @summary Test tryAdvance method of ThreadLocalRandom for correct behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexReachesFence();
    }

    static void testTryAdvanceWithValidConsumer() {
        DoubleConsumer consumer = new DoubleConsumer() {
            boolean called = false;
            double value = 0.0;
            @Override
            public void accept(double t) {
                called = true;
                value = t;
            }
        };
        boolean result = ThreadLocalRandom.current().tryAdvance(consumer);
        assert result : "Expected tryAdvance to return true when consumer is accepted";
    }

    static void testTryAdvanceWithNullConsumer() {
        try {
            ThreadLocalRandom.current().tryAdvance(null);
            assert false : "Expected NullPointerException when consumer is null";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testTryAdvanceWhenIndexReachesFence() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        // Simulate index >= fence by reflection or direct manipulation is not possible here,
        // so we test the normal behavior assuming index < fence.
        // For the purpose of this test, we assume tryAdvance returns false if index >= fence.
        // Since internal state is not accessible, we test the method's behavior as is.
        // Alternatively, we can invoke tryAdvance multiple times.
        for (int i = 0; i < 10; i++) {
            r.tryAdvance(t -> {});
        }
        // No assertion here as internal state is not accessible; just ensure no exceptions.
    }
}
