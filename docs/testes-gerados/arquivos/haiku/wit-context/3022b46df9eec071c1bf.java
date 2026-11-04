/*
 * @test
 * @summary Verify that the double-array spliterator getComparator throws IllegalStateException when not SORTED and returns null when SORTED
 * @run main SpliteratorsWitupTest
 */
import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        Spliterator.OfDouble unsorted = Spliterators.spliterator(new double[]{1.0, 2.0}, 0);
        boolean threw = false;
        try {
            unsorted.getComparator();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException for non-SORTED spliterator");
        }

        Spliterator.OfDouble sorted = Spliterators.spliterator(new double[]{1.0, 2.0}, Spliterator.SORTED);
        Comparator<? super Double> cmp = sorted.getComparator();
        if (cmp != null) {
            throw new RuntimeException("Expected null comparator for SORTED spliterator");
        }
    }
}
