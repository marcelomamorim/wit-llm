/*
 * @test
 * @summary Checks observable sorting behavior of IntStream.sorted() on SIZED sources, which reaches SortedOps.SizedIntSortingSink.begin(long)
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testSortedDistinctValues();
        testSortedEmptyStream();
        testSortedWithDuplicatesAndNegatives();
        testSortedSingleElement();
    }

    static void testSortedDistinctValues() {
        int[] actual = IntStream.of(5, 3, 1, 4, 2).sorted().toArray();
        int[] expected = {1, 2, 3, 4, 5};
        check(Arrays.equals(expected, actual), "distinct values not sorted: " + Arrays.toString(actual));
    }

    static void testSortedEmptyStream() {
        int[] actual = IntStream.empty().sorted().toArray();
        check(actual.length == 0, "empty stream should yield empty array");
    }

    static void testSortedWithDuplicatesAndNegatives() {
        int[] actual = IntStream.of(3, -1, 3, 0).sorted().toArray();
        int[] expected = {-1, 0, 3, 3};
        check(Arrays.equals(expected, actual), "duplicates/negatives not sorted: " + Arrays.toString(actual));
    }

    static void testSortedSingleElement() {
        int[] actual = IntStream.of(42).sorted().toArray();
        check(actual.length == 1 && actual[0] == 42, "single element changed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
