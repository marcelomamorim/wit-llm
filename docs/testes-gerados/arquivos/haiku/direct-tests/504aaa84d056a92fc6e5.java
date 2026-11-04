/*
 * @test
 * @summary Verifies PrimitiveIterator.OfDouble.nextDouble behavior via Spliterators.iterator(Spliterator.OfDouble)
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequentialNextDouble() {
        double[] data = {1.5, 2.5, 3.5};
        Spliterator.OfDouble sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        PrimitiveIterator.OfDouble it = Spliterators.iterator(sp);
        check(it.hasNext(), "expected hasNext true at start");
        check(it.nextDouble() == 1.5, "first value mismatch");
        check(it.nextDouble() == 2.5, "second value mismatch");
        check(it.nextDouble() == 3.5, "third value mismatch");
        check(!it.hasNext(), "expected hasNext false after exhaustion");
    }

    static void testHasNextDoesNotConsume() {
        double[] data = {7.0, 8.0};
        Spliterator.OfDouble sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        PrimitiveIterator.OfDouble it = Spliterators.iterator(sp);
        check(it.hasNext(), "hasNext first call should be true");
        check(it.hasNext(), "hasNext second call should be true");
        check(it.nextDouble() == 7.0, "hasNext must not consume the element");
        check(it.nextDouble() == 8.0, "second element mismatch after repeated hasNext");
    }

    static void testNextDoubleOnEmptyThrows() {
        double[] data = {};
        Spliterator.OfDouble sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        PrimitiveIterator.OfDouble it = Spliterators.iterator(sp);
        check(!it.hasNext(), "empty source should have no next element");
        boolean thrown = false;
        try {
            it.nextDouble();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "nextDouble on empty iterator must throw NoSuchElementException");
    }

    static void testNextDoubleAfterExhaustionThrows() {
        double[] data = {4.0};
        Spliterator.OfDouble sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        PrimitiveIterator.OfDouble it = Spliterators.iterator(sp);
        check(it.nextDouble() == 4.0, "single element mismatch");
        boolean thrown = false;
        try {
            it.nextDouble();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "nextDouble after exhaustion must throw NoSuchElementException");
    }

    public static void main(String[] args) {
        testSequentialNextDouble();
        testHasNextDoesNotConsume();
        testNextDoubleOnEmptyThrows();
        testNextDoubleAfterExhaustionThrows();
    }
}
