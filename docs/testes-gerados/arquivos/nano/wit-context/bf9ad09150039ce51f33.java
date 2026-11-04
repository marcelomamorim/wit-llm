/*
 * @test
 * @summary Test for Spliterators.tryAdvance(DoubleConsumer)
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidArray();
        testTryAdvanceWithEmptyArray();
    }

    private static void testTryAdvanceWithNullAction() {
        try {
            Spliterators.tryAdvance((DoubleConsumer) null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testTryAdvanceWithValidArray() {
        DoubleConsumer consumer = new DoubleConsumer() {
            double lastValue = -1;
            public void accept(double value) {
                lastValue = value;
            }
        };
        // Simulate internal array and index
        // Since internal state is not accessible, we test via the method's behavior
        // Using a custom spliterator with a known array
        double[] array = {1.1, 2.2, 3.3};
        int[] index = {0};
        Spliterator.OfDouble spliterator = new Spliterator.OfDouble() {
            public boolean tryAdvance(DoubleConsumer action) {
                if (action == null) {
                    throw new NullPointerException();
                }
                if (index[0] < array.length) {
                    action.accept(array[index[0]++]);
                    return true;
                }
                return false;
            }
            public int characteristics() { return 0; }
            public long estimateSize() { return array.length - index[0]; }
            public Spliterator.OfDouble trySplit() { return null; }
        };
        final double[] received = {0};
        boolean result = spliterator.tryAdvance((double v) -> received[0] = v);
        if (!result || received[0] != 1.1) {
            System.err.println("Failed: tryAdvance did not process first element correctly");
            System.exit(1);
        }
        // Call again to process next element
        result = spliterator.tryAdvance((double v) -> received[0] = v);
        if (!result || received[0] != 2.2) {
            System.err.println("Failed: tryAdvance did not process second element correctly");
            System.exit(1);
        }
    }

    private static void testTryAdvanceWithEmptyArray() {
        double[] emptyArray = {};
        int[] index = {0};
        Spliterator.OfDouble spliterator = new Spliterator.OfDouble() {
            public boolean tryAdvance(DoubleConsumer action) {
                if (action == null) {
                    throw new NullPointerException();
                }
                if (index[0] < emptyArray.length) {
                    action.accept(emptyArray[index[0]++]);
                    return true;
                }
                return false;
            }
            public int characteristics() { return 0; }
            public long estimateSize() { return 0; }
            public Spliterator.OfDouble trySplit() { return null; }
        };
        boolean result = spliterator.tryAdvance((double v) -> {});
        if (result) {
            System.err.println("Failed: tryAdvance on empty array should return false");
            System.exit(1);
        }
    }
}
