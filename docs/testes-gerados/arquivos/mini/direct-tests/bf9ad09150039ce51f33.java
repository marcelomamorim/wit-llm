/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance(DoubleConsumer) method behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.function.DoubleConsumer;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractDoubleSpliterator {
        final double[] array;
        int index;
        final int fence;

        TestSpliterator(double[] array, int origin, int fence) {
            super(fence - origin, 0);
            this.array = array;
            this.index = origin;
            this.fence = fence;
        }

        public boolean tryAdvance(DoubleConsumer action) {
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
        double[] data = {1.0, 2.0, 3.0};
        TestSpliterator spliterator = new TestSpliterator(data, 0, data.length);
        final double[] consumed = new double[3];
        int count = 0;
        while (spliterator.tryAdvance(new DoubleConsumer() {
            int i = 0;
            public void accept(double value) {
                consumed[i++] = value;
            }
        })) {
            count++;
        }
        if (count != data.length) {
            throw new RuntimeException("tryAdvance did not consume all elements");
        }
        for (int i = 0; i < data.length; i++) {
            if (consumed[i] != data[i]) {
                throw new RuntimeException("tryAdvance consumed wrong element at index " + i);
            }
        }
    }

    private static void testTryAdvanceEnd() {
        double[] data = {1.0};
        TestSpliterator spliterator = new TestSpliterator(data, 1, 1); // index == fence
        boolean result = spliterator.tryAdvance(new DoubleConsumer() {
            public void accept(double value) {
                throw new RuntimeException("Should not be called");
            }
        });
        if (result) {
            throw new RuntimeException("tryAdvance should return false when index >= fence");
        }
    }

    private static void testTryAdvanceNull() {
        double[] data = {1.0};
        TestSpliterator spliterator = new TestSpliterator(data, 0, 1);
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("tryAdvance should throw NullPointerException when action is null");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
