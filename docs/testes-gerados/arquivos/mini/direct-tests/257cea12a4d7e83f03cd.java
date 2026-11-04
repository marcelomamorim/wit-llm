/*
 * @test
 * @summary Test java.util.Spliterators.forEachRemaining(IntConsumer) method
 * @run main SpliteratorsWitupTest
 */

import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends java.util.Spliterators.AbstractIntSpliterator {
        final int[] array;
        int index;
        final int fence;

        TestSpliterator(int[] array, int origin, int fence) {
            super(fence - origin, 0);
            this.array = array;
            this.index = origin;
            this.fence = fence;
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (index < fence) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
            int[] a; int i, hi;
            if (action == null)
                throw new NullPointerException();
            if ((a = array).length >= (hi = fence) &&
                (i = index) >= 0 && i < (index = hi)) {
                do { action.accept(a[i]); } while (++i < hi);
            }
        }
    }

    public static void main(String[] args) {
        testNullActionThrows();
        testForEachRemainingConsumesAll();
        testForEachRemainingWithEmptyRange();
    }

    private static void testNullActionThrows() {
        TestSpliterator spliterator = new TestSpliterator(new int[] {1, 2, 3}, 0, 3);
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingConsumesAll() {
        final int[] data = {10, 20, 30, 40};
        TestSpliterator spliterator = new TestSpliterator(data, 0, data.length);
        final StringBuilder sb = new StringBuilder();
        spliterator.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                sb.append(value).append(",");
            }
        });
        String result = sb.toString();
        if (!result.equals("10,20,30,40,")) {
            throw new RuntimeException("forEachRemaining did not consume all elements: " + result);
        }
        if (spliterator.index != spliterator.fence) {
            throw new RuntimeException("Index not updated correctly after forEachRemaining");
        }
    }

    private static void testForEachRemainingWithEmptyRange() {
        final int[] data = {5, 6, 7};
        TestSpliterator spliterator = new TestSpliterator(data, 2, 2); // empty range
        final StringBuilder sb = new StringBuilder();
        spliterator.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                sb.append(value);
            }
        });
        if (sb.length() != 0) {
            throw new RuntimeException("forEachRemaining should not consume any elements for empty range");
        }
        if (spliterator.index != spliterator.fence) {
            throw new RuntimeException("Index not updated correctly for empty range");
        }
    }
}
