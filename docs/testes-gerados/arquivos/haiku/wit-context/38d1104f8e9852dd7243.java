/*
 * @test
 * @summary Observable behavior of sorted() on IntStream, which reaches IntSortingSink.begin with small sizes
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class SortedOpsWitupTest {
    public static void main(String[] args) {
        int[] sorted = IntStream.of(3, -1, 2, 2, 0).sorted().toArray();
        check(Arrays.equals(sorted, new int[]{-1, 0, 2, 2, 3}), "sorted ints with duplicates and negatives");

        long emptyCount = IntStream.empty().sorted().count();
        check(emptyCount == 0L, "sorted empty stream");

        int[] single = IntStream.of(7).sorted().toArray();
        check(Arrays.equals(single, new int[]{7}), "sorted single element");
    }

    static void check(boolean ok, String msg) {
        if (!ok) {
            throw new RuntimeException(msg);
        }
    }
}
