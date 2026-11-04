/*
 * @test
 * @summary Test java.util.Spliterators.forEachRemaining(LongConsumer) method
 * @run main SpliteratorsWitupTest
 */

import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    static class TestSpliterator {
        final long[] array;
        int index;
        final int fence;

        TestSpliterator(long[] array, int start, int fence) {
            this.array = array;
            this.index = start;
            this.fence = fence;
        }

        public void forEachRemaining(LongConsumer action) {
            long[] a; int i, hi; // hoist accesses and checks from loop
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
        testEmptyRange();
        testFullRange();
        testPartialRange();
    }

    static void testNullActionThrows() {
        TestSpliterator spliterator = new TestSpliterator(new long[] {1, 2, 3}, 0, 3);
        try {
            spliterator.forEachRemaining(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyRange() {
        final StringBuilder sb = new StringBuilder();
        TestSpliterator spliterator = new TestSpliterator(new long[] {1, 2, 3}, 3, 3);
        spliterator.forEachRemaining(new LongConsumer() {
            public void accept(long value) {
                sb.append(value);
            }
        });
        if (sb.length() != 0) {
            throw new Error("Expected no elements to be consumed, but got: " + sb.toString());
        }
    }

    static void testFullRange() {
        final StringBuilder sb = new StringBuilder();
        long[] data = {10L, 20L, 30L};
        TestSpliterator spliterator = new TestSpliterator(data, 0, data.length);
        spliterator.forEachRemaining(new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        });
        String result = sb.toString();
        if (!result.equals("10,20,30,")) {
            throw new Error("Expected '10,20,30,' but got: " + result);
        }
        if (spliterator.index != spliterator.fence) {
            throw new Error("Expected index to be updated to fence after forEachRemaining");
        }
    }

    static void testPartialRange() {
        final StringBuilder sb = new StringBuilder();
        long[] data = {5L, 6L, 7L, 8L, 9L};
        TestSpliterator spliterator = new TestSpliterator(data, 1, 4);
        spliterator.forEachRemaining(new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        });
        String result = sb.toString();
        if (!result.equals("6,7,8,")) {
            throw new Error("Expected '6,7,8,' but got: " + result);
        }
        if (spliterator.index != spliterator.fence) {
            throw new Error("Expected index to be updated to fence after forEachRemaining");
        }
    }
}
