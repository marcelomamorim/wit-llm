/* @test    @summary Tests for java.util.stream.SortedOps methods    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Comparator;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testMakeRefWithComparator();
        testBeginWithValidSize();
        testBeginWithLargeSizeThrows();
    }

    public static void testMakeRefWithComparator() {
        Stream<Integer> stream = Stream.of(3, 1, 2);
        Comparator<Integer> comp = Integer::compareTo;
        Stream<Integer> sortedStream = SortedOps.makeRef(stream, comp);
        Object[] result = sortedStream.toArray();
        boolean isSorted = true;
        for (int i = 1; i < result.length; i++) {
            if (((Integer) result[i - 1]) > ((Integer) result[i])) {
                isSorted = false;
                break;
            }
        }
        if (!isSorted) {
            throw new AssertionError("Stream is not sorted as expected.");
        }
    }

    public static void testBeginWithValidSize() {
        class Dummy {
            public boolean called = false;
        }
        Dummy dummy = new Dummy();
        try {
            java.util.stream.SortedOps.SizedRefSortingSink sink = new java.util.stream.SortedOps.SizedRefSortingSink();
            sink.begin(10);
            dummy.called = true;
        } catch (Exception e) {
            throw new AssertionError("begin(10) should not throw", e);
        }
    }

    public static void testBeginWithLargeSizeThrows() {
        try {
            java.util.stream.SortedOps.SizedRefSortingSink sink = new java.util.stream.SortedOps.SizedRefSortingSink();
            sink.begin(java.util.stream.Nodes.MAX_ARRAY_SIZE);
            throw new AssertionError("begin(MAX_ARRAY_SIZE) should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
