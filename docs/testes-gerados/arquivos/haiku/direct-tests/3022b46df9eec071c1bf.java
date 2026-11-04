/*
 * @test
 * @summary Verifies getComparator behavior of the empty DoubleSpliterator returned by Spliterators.emptyDoubleSpliterator()
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testEmptyDoubleSpliteratorIsNotSorted();
        testGetComparatorThrowsWhenNotSorted();
        testGetComparatorConsistentAcrossCalls();
    }

    static void testEmptyDoubleSpliteratorIsNotSorted() {
        Spliterator.OfDouble s = Spliterators.emptyDoubleSpliterator();
        if (s.hasCharacteristics(Spliterator.SORTED)) {
            throw new AssertionError("empty double spliterator unexpectedly reports SORTED");
        }
    }

    static void testGetComparatorThrowsWhenNotSorted() {
        Spliterator.OfDouble s = Spliterators.emptyDoubleSpliterator();
        boolean thrown = false;
        try {
            s.getComparator();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getComparator() did not throw IllegalStateException for a non-SORTED spliterator");
        }
    }

    static void testGetComparatorConsistentAcrossCalls() {
        Spliterator.OfDouble s = Spliterators.emptyDoubleSpliterator();
        for (int i = 0; i < 2; i++) {
            boolean thrown = false;
            try {
                s.getComparator();
            } catch (IllegalStateException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("getComparator() did not throw on call " + i);
            }
        }
    }
}
