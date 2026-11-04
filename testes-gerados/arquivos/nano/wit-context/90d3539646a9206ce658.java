/*
 * @test
 * @summary Tests for java.util.stream.SortedOps methods
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.Stream;
import java.util.Comparator;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testMakeRefWithComparator();
        testBeginWithValidSize();
        testBeginWithExceedingMaxSize();
    }

    public static void testMakeRefWithComparator() {
        Stream<Integer> stream = Stream.of(3, 1, 2);
        Comparator<Integer> comp = Integer::compareTo;
        Stream<Integer> sortedStream = SortedOps.makeRef(stream, comp);
        boolean sortedCorrectly = sortedStream.sorted(comp).toArray()[0] == 1;
        if (!sortedCorrectly) {
            throw new AssertionError("Sorted stream not sorted correctly with comparator");
        }
    }

    public static void testBeginWithValidSize() {
        try {
            SortedOps.begin(10L);
        } catch (Exception e) {
            throw new AssertionError("begin() threw an unexpected exception for size 10", e);
        }
    }

    public static void testBeginWithExceedingMaxSize() {
        long maxSize = 2147483639L; // a value >= Nodes.MAX_ARRAY_SIZE (assumed to be Integer.MAX_VALUE)
        try {
            SortedOps.begin(maxSize);
            throw new AssertionError("Expected IllegalArgumentException for size >= Nodes.MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
