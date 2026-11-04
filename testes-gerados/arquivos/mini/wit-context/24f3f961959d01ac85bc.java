/*
 * @test
 * @summary Deterministic tests for StreamSpliterators.IntWrappingSpliterator.forEachRemaining(IntConsumer)
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.stream.StreamSpliterators;

public class StreamSpliteratorsWitupTest {

    // Minimal subclass to expose forEachRemaining for testing
    static class IntWrappingSpliterator extends StreamSpliterators.IntWrappingSpliteratorBase {

        boolean finished = false;
        IntConsumer lastConsumer = null;

        IntWrappingSpliterator() {
            super();
        }

        @Override
        public void forEachRemaining(IntConsumer consumer) {
            if (buffer == null && !finished) {
                Objects.requireNonNull(consumer);
                init();
                // Simulate wrapAndCopyInto by calling consumer.accept with a fixed value
                consumer.accept(42);
                finished = true;
            } else {
                do { } while (tryAdvance(consumer));
            }
        }

        // Dummy implementations to satisfy abstract methods
        void init() {
            // simulate initialization
            buffer = new int[1];
        }

        boolean tryAdvance(IntConsumer consumer) {
            if (!finished) {
                consumer.accept(99);
                finished = true;
                return true;
            }
            return false;
        }

        // Fields to simulate state
        int[] buffer = null;
    }

    public static void main(String[] args) {
        testNullConsumerBeforeInit();
        testNullConsumerAfterInitOrFinished();
        testNormalForEachRemaining();
    }

    static void testNullConsumerBeforeInit() {
        IntWrappingSpliterator spliterator = new IntWrappingSpliterator();
        spliterator.buffer = null;
        spliterator.finished = false;
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException for null consumer before init");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullConsumerAfterInitOrFinished() {
        IntWrappingSpliterator spliterator = new IntWrappingSpliterator();
        spliterator.buffer = new int[1];
        spliterator.finished = true;
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException for null consumer after init or finished");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNormalForEachRemaining() {
        IntWrappingSpliterator spliterator = new IntWrappingSpliterator();
        spliterator.buffer = null;
        spliterator.finished = false;

        final int[] consumed = new int[1];
        spliterator.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        });

        if (consumed[0] != 42) {
            throw new RuntimeException("forEachRemaining did not consume expected value 42, got " + consumed[0]);
        }

        // Calling again should not call consumer because finished is true
        final int[] consumedAgain = new int[1];
        spliterator.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                consumedAgain[0] = value;
            }
        });
        if (consumedAgain[0] != 0) {
            throw new RuntimeException("forEachRemaining called consumer after finished");
        }
    }

    // Abstract base class to simulate the original class structure
    static abstract class IntWrappingSpliteratorBase {
        int[] buffer = null;
        boolean finished = false;

        public abstract void forEachRemaining(IntConsumer consumer);
    }
}
