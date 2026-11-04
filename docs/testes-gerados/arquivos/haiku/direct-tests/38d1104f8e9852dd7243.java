/*
 * @test
 * @summary Verifies observable sorting behavior of IntStream.sorted(), which uses SortedOps.makeInt and the begin(long size) sink for known, empty, unknown and larger sizes.
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testSmallUnsortedWithDuplicates();
        testEmptySource();
        testKnownSizeDescending();
        testUnknownSizeFiltered();
        testLargerDescendingSource();
    }

    static void testSmallUnsortedWithDuplicates() {
        int[] actual = IntStream.of(5, 3, 9, 1, 3).sorted().toArray();
        check(Arrays.equals(actual, new int[]{1, 3, 3, 5, 9}),
              "small unsorted with duplicates: " + Arrays.toString(actual));
    }

    static void testEmptySource() {
        int[] actual = IntStream.empty().sorted().toArray();
        check(actual.length == 0, "empty source must produce empty result");
    }

    static void testKnownSizeDescending() {
        int[] actual = IntStream.range(0, 5).map(i -> 4 - i).sorted().toArray();
        check(Arrays.equals(actual, new int[]{0, 1, 2, 3, 4}),
              "known size descending: " + Arrays.toString(actual));
    }

    static void testUnknownSizeFiltered() {
        int[] actual = IntStream.of(4, 2, 8, 6, 7, 1).filter(i -> i % 2 == 0).sorted().toArray();
        check(Arrays.equals(actual, new int[]{2, 4, 6, 8}),
              "unknown size filtered: " + Arrays.toString(actual));
    }

    static void testLargerDescendingSource() {
        int n = 10000;
        int[] actual = IntStream.range(0, n).map(i -> n - 1 - i).sorted().toArray();
        check(actual.length == n, "larger source length mismatch: " + actual.length);
        for (int i = 0; i < n; i++) {
            check(actual[i] == i, "larger source mismatch at index " + i + ": " + actual[i]);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
