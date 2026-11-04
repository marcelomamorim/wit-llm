/*
 * @test
 * @summary Witup-derived checks for Spliterators.DoubleIteratorSpliterator.forEachRemaining via public API
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPEAndDoesNotConsume();
        testForEachRemainingConsumesAllElementsInOrder();
        testEmptyIteratorReportsNoElements();
    }

    static PrimitiveIterator.OfDouble iter(final double... values) {
        return new PrimitiveIterator.OfDouble() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < values.length;
            }

            @Override
            public double nextDouble() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return values[i++];
            }
        };
    }

    static void testNullActionThrowsNPEAndDoesNotConsume() {
        Spliterator.OfDouble s = Spliterators.spliterator(iter(1.0, 2.0), 2, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            s.forEachRemaining((DoubleConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
        final double[] buf = new double[2];
        final int[] n = {0};
        s.forEachRemaining((DoubleConsumer) d -> buf[n[0]++] = d);
        if (n[0] != 2 || buf[0] != 1.0 || buf[1] != 2.0) {
            throw new RuntimeException("iterator was consumed despite NPE: count=" + n[0]);
        }
    }

    static void testForEachRemainingConsumesAllElementsInOrder() {
        Spliterator.OfDouble s = Spliterators.spliterator(iter(3.5, -1.0, 0.0), 3, Spliterator.ORDERED);
        final double[] buf = new double[3];
        final int[] n = {0};
        s.forEachRemaining((DoubleConsumer) d -> buf[n[0]++] = d);
        if (n[0] != 3 || buf[0] != 3.5 || buf[1] != -1.0 || buf[2] != 0.0) {
            throw new RuntimeException("unexpected elements: count=" + n[0]);
        }
        final int[] again = {0};
        s.forEachRemaining((DoubleConsumer) d -> again[0]++);
        if (again[0] != 0) {
            throw new RuntimeException("elements delivered twice");
        }
    }

    static void testEmptyIteratorReportsNoElements() {
        Spliterator.OfDouble s = Spliterators.spliterator(iter(), 0, Spliterator.ORDERED);
        final int[] n = {0};
        s.forEachRemaining((DoubleConsumer) d -> n[0]++);
        if (n[0] != 0) {
            throw new RuntimeException("unexpected elements from empty iterator");
        }
    }
}
