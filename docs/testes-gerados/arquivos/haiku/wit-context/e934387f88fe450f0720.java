/*
 * @test
 * @summary Verify LongIteratorSpliterator.getComparator behavior through the public Spliterators.spliterator factory
 * @run main SpliteratorsWitupTest
 */
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.LongStream;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        PrimitiveIterator.OfLong it1 = LongStream.of(1L, 2L).iterator();
        Spliterator.OfLong unsorted = Spliterators.spliterator(it1, 2L, Spliterator.ORDERED);
        try {
            unsorted.getComparator();
            throw new AssertionError("expected IllegalStateException when SORTED is absent");
        } catch (IllegalStateException expected) {
        }

        PrimitiveIterator.OfLong it2 = LongStream.of(1L, 2L).iterator();
        Spliterator.OfLong sorted = Spliterators.spliterator(it2, 2L,
                Spliterator.ORDERED | Spliterator.SORTED);
        if (sorted.getComparator() != null) {
            throw new AssertionError("expected null comparator when SORTED is present");
        }
    }
}
