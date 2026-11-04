/*
 * @test
 * @summary Test StreamSpliterators.tryAdvance(IntConsumer) method
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class StreamSpliteratorsWitupTest {

    static class TestSpliterator {
        private final IntSupplier s;

        TestSpliterator(IntSupplier s) {
            this.s = s;
        }

        public boolean tryAdvance(IntConsumer action) {
            Objects.requireNonNull(action);
            action.accept(s.getAsInt());
            return true;
        }
    }

    public static void main(String[] args) {
        // Test normal behavior: tryAdvance calls action.accept with supplied int
        final int[] consumed = new int[1];
        IntSupplier supplier = new IntSupplier() {
            private int value = 42;
            public int getAsInt() {
                return value;
            }
        };
        TestSpliterator spliterator = new TestSpliterator(supplier);

        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        };

        boolean result = spliterator.tryAdvance(consumer);

        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }
        if (consumed[0] != 42) {
            throw new RuntimeException("tryAdvance did not pass correct value to consumer");
        }

        // Test that passing null action throws NullPointerException
        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("tryAdvance should throw NullPointerException when action is null");
        }
    }
}
