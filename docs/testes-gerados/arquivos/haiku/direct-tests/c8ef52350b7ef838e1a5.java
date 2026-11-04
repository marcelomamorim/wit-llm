/*
 * @test
 * @summary Spliterators double spliterator getComparator returns null when SORTED and throws IllegalStateException otherwise
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        double[] data = {1.0, 2.0, 3.0};

        Spliterator.OfDouble sorted = Spliterators.spliterator(data, 0, data.length,
                Spliterator.SORTED | Spliterator.ORDERED);
        if (sorted.getComparator() != null) {
            throw new AssertionError("expected null comparator when SORTED is reported");
        }

        Spliterator.OfDouble unsorted = Spliterators.spliterator(data, 0, data.length,
                Spliterator.ORDERED);
        boolean thrown = false;
        try {
            unsorted.getComparator();
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalStateException when SORTED is not reported");
        }
    }
}
