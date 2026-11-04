/*
 * @test
 * @summary Verifies Spliterator.getComparator behavior for iterator-based spliterators created via public Spliterators factories
 * @run main SpliteratorsWitupTest
 */
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testUnsortedSizedThrows();
        testSortedSizedReturnsNull();
        testUnsortedUnknownSizeThrows();
        testSortedUnknownSizeReturnsNull();
    }

    static void testUnsortedSizedThrows() {
        List<String> data = Arrays.asList("a", "b");
        Spliterator<String> s = Spliterators.spliterator(data.iterator(), 2, Spliterator.ORDERED);
        expectIllegalState(s);
    }

    static void testSortedSizedReturnsNull() {
        List<String> data = Arrays.asList("a", "b");
        Spliterator<String> s = Spliterators.spliterator(data.iterator(), 2,
                Spliterator.ORDERED | Spliterator.SORTED);
        Comparator<? super String> c = s.getComparator();
        if (c != null) {
            throw new AssertionError("expected null comparator for SORTED spliterator");
        }
    }

    static void testUnsortedUnknownSizeThrows() {
        List<String> data = Arrays.asList("a", "b");
        Spliterator<String> s = Spliterators.spliteratorUnknownSize(data.iterator(), Spliterator.ORDERED);
        expectIllegalState(s);
    }

    static void testSortedUnknownSizeReturnsNull() {
        List<String> data = Arrays.asList("a", "b");
        Spliterator<String> s = Spliterators.spliteratorUnknownSize(data.iterator(),
                Spliterator.ORDERED | Spliterator.SORTED);
        Comparator<? super String> c = s.getComparator();
        if (c != null) {
            throw new AssertionError("expected null comparator for SORTED spliterator");
        }
    }

    static void expectIllegalState(Spliterator<String> s) {
        try {
            s.getComparator();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new AssertionError("expected IllegalStateException when SORTED is not reported");
    }
}
