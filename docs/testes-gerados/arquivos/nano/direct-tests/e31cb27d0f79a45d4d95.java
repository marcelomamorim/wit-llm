/* @test    @summary Test tryAdvance method of SplittableRandom for DoubleConsumer behavior    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexReachesFence();
    }

    static class DummyDoubleConsumer implements DoubleConsumer {
        boolean accepted = false;
        double value = 0.0;
        @Override
        public void accept(double t) {
            accepted = true;
            value = t;
        }
    }

    static void testTryAdvanceWithValidConsumer() {
        SplittableRandom rng = new SplittableRandom();
        DummyDoubleConsumer consumer = new DummyDoubleConsumer();
        boolean result = rng.tryAdvance(consumer);
        assert result : "Expected tryAdvance to return true when index < fence";
        assert consumer.accepted : "Consumer should have been accepted";
        assert consumer.value >= 0.0 && consumer.value <= 1.0 : "Generated value should be within [0.0, 1.0]";
    }

    static void testTryAdvanceWithNullConsumer() {
        SplittableRandom rng = new SplittableRandom();
        try {
            rng.tryAdvance(null);
            assert false : "Expected NullPointerException when consumer is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testTryAdvanceWhenIndexReachesFence() {
        SplittableRandom rng = new SplittableRandom();
        // Manually set index >= fence to simulate end condition
        // Note: index and fence are private; assuming we can set via reflection or subclassing, but as per constraints, we simulate by subclassing
        class TestSplittableRandom extends SplittableRandom {
            long index = 10;
            long fence = 10;
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                // override to set index and fence to simulate condition
                this.index = 10;
                this.fence = 10;
                return super.tryAdvance(consumer);
            }
        }
        TestSplittableRandom testRng = new TestSplittableRandom();
        DummyDoubleConsumer consumer = new DummyDoubleConsumer();
        boolean result = testRng.tryAdvance(consumer);
        assert !result : "Expected tryAdvance to return false when index >= fence";
    }
}
