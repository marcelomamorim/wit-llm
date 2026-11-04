/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance(LongConsumer) behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.function.LongConsumer;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractLongSpliterator {
        final long[] array;
        int index;
        final int fence;

        TestSpliterator(long[] array, int origin, int fence) {
            super(fence - origin, 0);
            this.array = array;
            this.index = origin;
            this.fence = fence;
        }

        public boolean tryAdvance(LongConsumer action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceNormal();
        testTryAdvanceEnd();
        testTryAdvanceNull();
    }

    private static void testTryAdvanceNormal() {
        long[] data = {10L, 20L, 30L};
        TestSpliterator spliterator = new TestSpliterator(data, 0, data.length);
        final long[] consumed = new long[3];
        for (int i = 0; i < data.length; i++) {
            final int idx = i;
            boolean advanced = spliterator.tryAdvance(new LongConsumer() {
                public void accept(long value) {
                    consumed[idx] = value;
                }
            });
            if (!advanced) {
                throw new RuntimeException("tryAdvance returned false prematurely at index " + i);
            }
        }
        // After consuming all elements, tryAdvance should return false
        boolean advanced = spliterator.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                // no-op
            }
        });
        if (advanced) {
            throw new RuntimeException("tryAdvance returned true after all elements consumed");
        }
        // Check consumed values
        for (int i = 0; i < data.length; i++) {
            if (consumed[i] != data[i]) {
                throw new RuntimeException("Consumed value mismatch at index " + i + ": expected " + data[i] + ", got " + consumed[i]);
            }
        }
    }

    private static void testTryAdvanceEnd() {
        long[] data = {1L};
        TestSpliterator spliterator = new TestSpliterator(data, 1, 1); // index == fence
        boolean advanced = spliterator.tryAdvance(new LongConsumer() {
            public void accept(long value) {
                throw new RuntimeException("Should not be called");
            }
        });
        if (advanced) {
            throw new RuntimeException("tryAdvance returned true when index == fence");
        }
    }

    private static void testTryAdvanceNull() {
        long[] data = {1L};
        TestSpliterator spliterator = new TestSpliterator(data, 0, 1);
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
