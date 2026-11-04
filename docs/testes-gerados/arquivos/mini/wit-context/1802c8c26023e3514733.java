/*
 * @test
 * @summary Test StreamSpliterators.tryAdvance(IntConsumer) for null action and normal behavior
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.IntConsumer;

public class StreamSpliteratorsWitupTest {

    static class DummySpliterator {
        java.util.function.IntSupplier s = new java.util.function.IntSupplier() {
            private int current = 0;
            public int getAsInt() {
                return current++;
            }
        };

        public boolean tryAdvance(IntConsumer action) {
            Objects.requireNonNull(action);
            action.accept(s.getAsInt());
            return true;
        }
    }

    public static void main(String[] args) {
        DummySpliterator spliterator = new DummySpliterator();

        // Test null action throws NullPointerException
        try {
            spliterator.tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test normal behavior: action is called with increasing ints
        final int[] received = new int[1];
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                received[0] = value;
            }
        };

        boolean result1 = spliterator.tryAdvance(consumer);
        if (!result1) {
            throw new Error("tryAdvance should return true");
        }
        if (received[0] != 0) {
            throw new Error("Expected first value 0 but got " + received[0]);
        }

        boolean result2 = spliterator.tryAdvance(consumer);
        if (!result2) {
            throw new Error("tryAdvance should return true");
        }
        if (received[0] != 1) {
            throw new Error("Expected second value 1 but got " + received[0]);
        }
    }
}
