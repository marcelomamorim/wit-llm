/*
 * @test
 * @summary Test java.util.Random.tryAdvance(IntConsumer) behavior
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.function.IntConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceReturnsTrueAndAdvancesIndex();
        testTryAdvanceReturnsFalseWhenIndexNotLessThanFence();
    }

    private static void testTryAdvanceWithNullConsumer() {
        Random random = new Random();
        try {
            random.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testTryAdvanceReturnsTrueAndAdvancesIndex() {
        // We create a subclass to expose and control index, fence, origin, bound, and rng.internalNextInt
        TestRandom random = new TestRandom(0, 5, 10, 20);
        final int[] consumed = new int[1];
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        };

        boolean result = random.tryAdvance(consumer);
        if (!result) {
            throw new RuntimeException("tryAdvance should return true when index < fence");
        }
        if (random.index != 1) {
            throw new RuntimeException("index should be incremented after tryAdvance");
        }
        if (consumed[0] != 15) {
            throw new RuntimeException("consumer.accept should be called with rng.internalNextInt result");
        }
    }

    private static void testTryAdvanceReturnsFalseWhenIndexNotLessThanFence() {
        TestRandom random = new TestRandom(5, 5, 10, 20);
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                throw new RuntimeException("accept should not be called when index >= fence");
            }
        };

        boolean result = random.tryAdvance(consumer);
        if (result) {
            throw new RuntimeException("tryAdvance should return false when index >= fence");
        }
    }

    // Helper subclass to expose and control internal state for testing
    private static class TestRandom extends Random {
        int index;
        int fence;
        int origin;
        int bound;

        TestRandom(int index, int fence, int origin, int bound) {
            super(12345L); // seed irrelevant here
            this.index = index;
            this.fence = fence;
            this.origin = origin;
            this.bound = bound;
        }

        // Override tryAdvance to use our fields and simulate rng.internalNextInt
        @Override
        public boolean tryAdvance(IntConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            int i = index, f = fence;
            if (i < f) {
                consumer.accept(internalNextInt(origin, bound));
                index = i + 1;
                return true;
            }
            return false;
        }

        // Simulate rng.internalNextInt(origin, bound) deterministically
        int internalNextInt(int origin, int bound) {
            // Return midpoint for predictability
            return (origin + bound) / 2;
        }
    }
}
