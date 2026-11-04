/*
 * @test
 * @summary Verifies NoSuchElementException from the iterator adapter nextLong on empty and exhausted spliterators
 * @run main SpliteratorsWitupTest
 */

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.LongStream;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testEmptyIteratorNextLongThrows();
        testExhaustedIteratorNextLongThrows();
    }

    private static void testEmptyIteratorNextLongThrows() {
        PrimitiveIterator.OfLong it =
                Spliterators.iterator(Spliterators.emptyLongSpliterator());
        if (it.hasNext()) {
            throw new AssertionError("empty iterator must not have next");
        }
        try {
            it.nextLong();
            throw new AssertionError("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    private static void testExhaustedIteratorNextLongThrows() {
        Spliterator.OfLong sp = LongStream.of(1L, 2L).spliterator();
        PrimitiveIterator.OfLong it = Spliterators.iterator(sp);
        if (it.nextLong() != 1L) {
            throw new AssertionError("first element mismatch");
        }
        if (it.nextLong() != 2L) {
            throw new AssertionError("second element mismatch");
        }
        if (it.hasNext()) {
            throw new AssertionError("iterator must be exhausted");
        }
        try {
            it.nextLong();
            throw new AssertionError("expected NoSuchElementException after exhaustion");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
