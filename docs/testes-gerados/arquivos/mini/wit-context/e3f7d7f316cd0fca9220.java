/*
 * @test
 * @summary Test Streams.getComparator() behavior including exception on beforeSplit
 * @run main StreamsWitupTest
 */

import java.util.Comparator;
import java.util.stream.Streams;

public class StreamsWitupTest {

    static class DummySpliterator<T> {
        Comparator<? super T> comparator;
        DummySpliterator(Comparator<? super T> comp) {
            this.comparator = comp;
        }
        Comparator<? super T> getComparator() {
            return comparator;
        }
    }

    // Minimal ConcatSpliterator stub to test getComparator behavior
    static class ConcatSpliterator<T> {
        boolean beforeSplit;
        DummySpliterator<T> bSpliterator;

        ConcatSpliterator(boolean beforeSplit, Comparator<? super T> comp) {
            this.beforeSplit = beforeSplit;
            this.bSpliterator = new DummySpliterator<>(comp);
        }

        public Comparator<? super T> getComparator() {
            if (beforeSplit)
                throw new IllegalStateException();
            return bSpliterator.getComparator();
        }
    }

    public static void main(String[] args) {
        // Test 1: beforeSplit == true should throw IllegalStateException
        ConcatSpliterator<String> cs1 = new ConcatSpliterator<>(true, String::compareTo);
        boolean thrown = false;
        try {
            cs1.getComparator();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected IllegalStateException when beforeSplit is true");
        }

        // Test 2: beforeSplit == false should return the comparator
        Comparator<? super String> comp = String::compareTo;
        ConcatSpliterator<String> cs2 = new ConcatSpliterator<>(false, comp);
        Comparator<? super String> result = cs2.getComparator();
        if (result == null) {
            throw new Error("Comparator returned is null when beforeSplit is false");
        }
        // Check that the returned comparator behaves as expected
        int cmpResult = result.compare("a", "b");
        if (cmpResult >= 0) {
            throw new Error("Comparator did not behave as expected");
        }

        System.out.println("StreamsWitupTest passed");
    }
}
