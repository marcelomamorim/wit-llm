/*
 * @test
 * @summary Test IntStream.tryAdvance method behavior
 * @run main IntStreamWitupTest
 */

import java.util.function.IntConsumer;
import java.util.Objects;

public class IntStreamWitupTest {

    static class TestIntStream {
        private boolean started = false;
        private int prev = 0;
        private final int seed;
        private final java.util.function.IntUnaryOperator f;

        TestIntStream(int seed, java.util.function.IntUnaryOperator f) {
            this.seed = seed;
            this.f = f;
        }

        public boolean tryAdvance(IntConsumer action) {
            Objects.requireNonNull(action);
            int t;
            if (started)
                t = f.applyAsInt(prev);
            else {
                t = seed;
                started = true;
            }
            action.accept(prev = t);
            return true;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceInitialSeed();
        testTryAdvanceSubsequentApply();
        testTryAdvanceNullAction();
    }

    private static void testTryAdvanceInitialSeed() {
        final int seed = 5;
        TestIntStream stream = new TestIntStream(seed, x -> x + 1);
        final int[] consumed = new int[1];
        boolean result = stream.tryAdvance(new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        });
        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }
        if (consumed[0] != seed) {
            throw new RuntimeException("Expected initial seed value " + seed + ", got " + consumed[0]);
        }
    }

    private static void testTryAdvanceSubsequentApply() {
        final int seed = 3;
        TestIntStream stream = new TestIntStream(seed, x -> x * 2);
        final int[] consumed = new int[2];

        // First call returns seed
        stream.tryAdvance(new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        });

        // Second call applies function to previous value
        stream.tryAdvance(new IntConsumer() {
            public void accept(int value) {
                consumed[1] = value;
            }
        });

        if (consumed[0] != seed) {
            throw new RuntimeException("First tryAdvance expected seed " + seed + ", got " + consumed[0]);
        }
        int expectedSecond = seed * 2;
        if (consumed[1] != expectedSecond) {
            throw new RuntimeException("Second tryAdvance expected " + expectedSecond + ", got " + consumed[1]);
        }
    }

    private static void testTryAdvanceNullAction() {
        final int seed = 1;
        TestIntStream stream = new TestIntStream(seed, x -> x);
        try {
            stream.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
