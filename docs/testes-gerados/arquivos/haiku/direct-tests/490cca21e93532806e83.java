/*
 * @test
 * @summary Verifies Spliterator.getComparator() behavior for spliterators created by Spliterators.spliterator(Iterator, long, int)
 * @run main SpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<String> sampleList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        return list;
    }

    static void testSortedReturnsNullComparator() {
        Iterator<String> it = sampleList().iterator();
        Spliterator<String> s = Spliterators.spliterator(it, 3, Spliterator.SORTED);
        Comparator<? super String> c = s.getComparator();
        check(c == null, "Expected null comparator for SORTED spliterator");
    }

    static void testSortedWithOtherFlagsReturnsNull() {
        Iterator<String> it = sampleList().iterator();
        int chars = Spliterator.SORTED | Spliterator.ORDERED | Spliterator.NONNULL;
        Spliterator<String> s = Spliterators.spliterator(it, 3, chars);
        check(s.getComparator() == null, "Expected null comparator when SORTED is combined with other flags");
    }

    static void testNotSortedThrowsIllegalStateException() {
        Iterator<String> it = sampleList().iterator();
        Spliterator<String> s = Spliterators.spliterator(it, 3, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            s.getComparator();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "Expected IllegalStateException when SORTED is not reported");
    }

    static void testZeroCharacteristicsThrowsIllegalStateException() {
        Iterator<String> it = sampleList().iterator();
        Spliterator<String> s = Spliterators.spliterator(it, 3, 0);
        boolean thrown = false;
        try {
            s.getComparator();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "Expected IllegalStateException for spliterator without SORTED characteristic");
    }

    public static void main(String[] args) {
        testSortedReturnsNullComparator();
        testSortedWithOtherFlagsReturnsNull();
        testNotSortedThrowsIllegalStateException();
        testZeroCharacteristicsThrowsIllegalStateException();
    }
}
