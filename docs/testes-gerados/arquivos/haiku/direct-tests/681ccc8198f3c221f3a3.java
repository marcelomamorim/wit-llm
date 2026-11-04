/*
 * @test
 * @summary Verifies observable ordering behavior of LongStream.sorted(), which exercises SortedOps.opWrapSink for longs
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class SortedOpsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUnsortedInput() {
        long[] result = LongStream.of(3L, 1L, 2L).sorted().toArray();
        check(Arrays.equals(result, new long[] {1L, 2L, 3L}),
              "sorted() on sized stream should order elements ascending: " + Arrays.toString(result));
    }

    static void testEmptyStream() {
        long[] result = LongStream.empty().sorted().toArray();
        check(result.length == 0, "sorted() on empty stream should produce no elements");
    }

    static void testDuplicatesAndNegatives() {
        long[] result = LongStream.of(5L, -2L, 5L, 0L, -2L).sorted().toArray();
        check(Arrays.equals(result, new long[] {-2L, -2L, 0L, 5L, 5L}),
              "sorted() should keep duplicates and negatives in order: " + Arrays.toString(result));
    }

    static void testUnsizedStream() {
        long[] result = LongStream.of(4L, 2L, 9L, 1L)
                .filter(x -> x != 0L)
                .sorted()
                .toArray();
        check(Arrays.equals(result, new long[] {1L, 2L, 4L, 9L}),
              "sorted() on unsized stream should order elements ascending: " + Arrays.toString(result));
    }

    static void testMinimumViaFindFirst() {
        long first = LongStream.of(7L, 3L, 8L).sorted().findFirst().getAsLong();
        check(first == 3L, "first element after sorted() should be the minimum, was " + first);
    }

    static void testAlreadySortedInput() {
        long[] result = LongStream.rangeClosed(1L, 5L).sorted().toArray();
        check(Arrays.equals(result, new long[] {1L, 2L, 3L, 4L, 5L}),
              "sorted() on already sorted input should preserve order: " + Arrays.toString(result));
    }

    public static void main(String[] args) {
        testUnsortedInput();
        testEmptyStream();
        testDuplicatesAndNegatives();
        testUnsizedStream();
        testMinimumViaFindFirst();
        testAlreadySortedInput();
    }
}
