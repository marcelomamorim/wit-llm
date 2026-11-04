/*
 * @test
 * @summary Verify PrimitiveIterator.OfDouble adapter nextDouble throws NoSuchElementException when exhausted and returns elements in order otherwise
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        testEmptyIteratorThrows();
        testValuesThenThrow();
        testRepeatedCallsAfterExhaustionThrow();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void testEmptyIteratorThrows() {
        PrimitiveIterator.OfDouble it =
                Spliterators.iterator(Spliterators.emptyDoubleSpliterator());
        if (it.hasNext()) {
            fail("empty iterator should not have next");
        }
        try {
            it.nextDouble();
            fail("nextDouble on empty iterator should throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    private static void testValuesThenThrow() {
        double[] data = {1.5, -2.0};
        PrimitiveIterator.OfDouble it =
                Spliterators.iterator(Spliterators.spliterator(data, Spliterator.ORDERED));
        check(it.nextDouble() == 1.5, "first element mismatch");
        check(it.nextDouble() == -2.0, "second element mismatch");
        try {
            it.nextDouble();
            fail("nextDouble past end should throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    private static void testRepeatedCallsAfterExhaustionThrow() {
        double[] data = {3.25};
        PrimitiveIterator.OfDouble it =
                Spliterators.iterator(Spliterators.spliterator(data, Spliterator.ORDERED));
        check(it.nextDouble() == 3.25, "element mismatch");
        for (int i = 0; i < 2; i++) {
            try {
                it.nextDouble();
                fail("exhausted nextDouble should throw on call " + i);
            } catch (NoSuchElementException expected) {
                // expected
            }
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    private static void fail(String message) {
        failures++;
        System.err.println("FAIL: " + message);
    }
}
