/*
 * @test
 * @summary Verifies LongStream.sorted() (which drives SortedOps.begin(long)) for sized and unsized upstreams
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

    public static void main(String[] args) {
        long[] input = {5L, -3L, 9L, 0L, 5L, Long.MIN_VALUE, Long.MAX_VALUE};
        long[] expected = input.clone();
        Arrays.sort(expected);

        long[] sized = LongStream.of(input).sorted().toArray();
        check(Arrays.equals(expected, sized), "sized upstream not sorted correctly");

        long[] unsized = LongStream.of(input).filter(x -> true).sorted().toArray();
        check(Arrays.equals(expected, unsized), "unsized upstream not sorted correctly");

        long[] empty = LongStream.empty().sorted().toArray();
        check(empty.length == 0, "empty stream should produce empty array");

        long[] single = LongStream.of(42L).sorted().toArray();
        check(Arrays.equals(new long[] {42L}, single), "single element stream mismatch");

        long[] reversed = LongStream.of(3L, 2L, 1L).sorted().toArray();
        check(Arrays.equals(new long[] {1L, 2L, 3L}, reversed), "reversed input not sorted");
    }
}
