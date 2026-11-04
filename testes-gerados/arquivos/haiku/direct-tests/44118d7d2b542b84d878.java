/*
 * @test
 * @summary Verifies forEachRemaining of the Spliterator.OfDouble wrapper created by Spliterators over a PrimitiveIterator.OfDouble
 * @run main SpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static PrimitiveIterator.OfDouble iter(final double[] values) {
        return new PrimitiveIterator.OfDouble() {
            int i = 0;
            public boolean hasNext() {
                return i < values.length;
            }
            public double nextDouble() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return values[i++];
            }
        };
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNPE() {
        Spliterator.OfDouble s =
            Spliterators.spliterator(iter(new double[] {1.0}), 1, Spliterator.ORDERED);
        try {
            s.forEachRemaining((java.util.function.DoubleConsumer) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingVisitsAllInOrder() {
        double[] values = {1.5, -2.0, 3.25};
        Spliterator.OfDouble s =
            Spliterators.spliterator(iter(values), values.length, Spliterator.ORDERED);
        List<Double> seen = new ArrayList<>();
        s.forEachRemaining((java.util.function.DoubleConsumer) seen::add);
        check(seen.size() == values.length, "wrong count: " + seen.size());
        for (int i = 0; i < values.length; i++) {
            check(seen.get(i) == values[i], "wrong element at " + i);
        }
    }

    static void testEmptyIteratorVisitsNothing() {
        Spliterator.OfDouble s =
            Spliterators.spliteratorUnknownSize(iter(new double[0]), Spliterator.ORDERED);
        final int[] calls = {0};
        s.forEachRemaining((java.util.function.DoubleConsumer) d -> calls[0]++);
        check(calls[0] == 0, "action invoked for empty iterator");
    }

    static void testIteratorExhaustedAfterForEachRemaining() {
        double[] values = {4.0, 5.0};
        Spliterator.OfDouble s =
            Spliterators.spliteratorUnknownSize(iter(values), Spliterator.ORDERED);
        final int[] calls = {0};
        s.forEachRemaining((java.util.function.DoubleConsumer) d -> calls[0]++);
        check(calls[0] == 2, "expected 2 calls, got " + calls[0]);
        boolean advanced = s.tryAdvance((java.util.function.DoubleConsumer) d -> { });
        check(!advanced, "spliterator should be exhausted");
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachRemainingVisitsAllInOrder();
        testEmptyIteratorVisitsNothing();
        testIteratorExhaustedAfterForEachRemaining();
    }
}
