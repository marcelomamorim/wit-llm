/*
 * @test
 * @summary Verifies Spliterators.iterator(OfLong).nextLong() returns elements in order, handles the hasNext/nextLong interleaving, and throws NoSuchElementException when exhausted
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        long[] data = {10L, -1L, Long.MAX_VALUE};

        PrimitiveIterator.OfLong it =
                Spliterators.iterator(Spliterators.spliterator(data, Spliterator.ORDERED));
        check(it.nextLong() == 10L);
        check(it.nextLong() == -1L);
        check(it.nextLong() == Long.MAX_VALUE);
        check(!it.hasNext());
        check(throwsNoSuchElement(it));

        PrimitiveIterator.OfLong peek =
                Spliterators.iterator(Spliterators.spliterator(data, Spliterator.ORDERED));
        check(peek.hasNext());
        check(peek.hasNext());
        check(peek.nextLong() == 10L);
        check(peek.nextLong() == -1L);

        PrimitiveIterator.OfLong empty =
                Spliterators.iterator(Spliterators.emptyLongSpliterator());
        check(!empty.hasNext());
        check(throwsNoSuchElement(empty));
    }

    private static boolean throwsNoSuchElement(PrimitiveIterator.OfLong it) {
        try {
            it.nextLong();
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    private static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }
}
