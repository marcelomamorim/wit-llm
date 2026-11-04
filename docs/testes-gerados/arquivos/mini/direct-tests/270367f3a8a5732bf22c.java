/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance(LongConsumer) method
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.PrimitiveIterator;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractLongSpliterator {
        private final PrimitiveIterator.OfLong it;

        TestSpliterator(long[] array) {
            super(array.length, 0);
            this.it = new PrimitiveIterator.OfLong() {
                private int index = 0;
                public boolean hasNext() {
                    return index < array.length;
                }
                public long nextLong() {
                    return array[index++];
                }
            };
        }

        public boolean tryAdvance(LongConsumer action) {
            if (action == null) throw new NullPointerException();
            if (it.hasNext()) {
                action.accept(it.nextLong());
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceEmpty();
        testTryAdvanceNullAction();
    }

    private static void testTryAdvanceWithElements() {
        final long[] values = {10L, 20L, 30L};
        TestSpliterator spliterator = new TestSpliterator(values);
        final long[] consumed = new long[values.length];
        final int[] index = {0};

        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                consumed[index[0]++] = value;
            }
        };

        for (int i = 0; i < values.length; i++) {
            boolean advanced = spliterator.tryAdvance(consumer);
            if (!advanced) {
                throw new RuntimeException("tryAdvance returned false prematurely at iteration " + i);
            }
        }

        // After all elements consumed, tryAdvance returns false
        if (spliterator.tryAdvance(consumer)) {
            throw new RuntimeException("tryAdvance returned true after all elements consumed");
        }

        // Check consumed values match input
        for (int i = 0; i < values.length; i++) {
            if (consumed[i] != values[i]) {
                throw new RuntimeException("Consumed value mismatch at index " + i + ": expected " + values[i] + ", got " + consumed[i]);
            }
        }
    }

    private static void testTryAdvanceEmpty() {
        TestSpliterator spliterator = new TestSpliterator(new long[0]);
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                throw new RuntimeException("accept should not be called on empty spliterator");
            }
        };
        if (spliterator.tryAdvance(consumer)) {
            throw new RuntimeException("tryAdvance returned true on empty spliterator");
        }
    }

    private static void testTryAdvanceNullAction() {
        TestSpliterator spliterator = new TestSpliterator(new long[] {1L});
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
