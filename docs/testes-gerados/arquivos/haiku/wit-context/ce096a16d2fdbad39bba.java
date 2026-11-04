/*
 * @test
 * @summary Basic observable behavior of LongStream.sorted() that exercises SortedOps.OfLong and its LongSortingSink begin() path with small sizes
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class SortedOpsWitupTest {
    public static void main(String[] args) {
        long[] sorted = LongStream.of(5L, -1L, 3L, 3L, 0L).map(x -> x).sorted().toArray();
        if (!Arrays.equals(sorted, new long[] {-1L, 0L, 3L, 3L, 5L})) {
            throw new RuntimeException("unexpected order: " + Arrays.toString(sorted));
        }

        long[] unsortedInput = LongStream.of(3L, 1L, 2L).map(x -> x).sorted().toArray();
        if (!Arrays.equals(unsortedInput, new long[] {1L, 2L, 3L})) {
            throw new RuntimeException("unexpected order: " + Arrays.toString(unsortedInput));
        }

        long[] empty = LongStream.empty().map(x -> x).sorted().toArray();
        if (empty.length != 0) {
            throw new RuntimeException("expected empty result, got length " + empty.length);
        }
    }
}
