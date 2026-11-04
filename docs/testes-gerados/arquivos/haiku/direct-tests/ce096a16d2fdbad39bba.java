/*
 * @test
 * @summary Exercises the LongStream sorted() path that calls SortedOps begin(long size) through public API
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.Random;
import java.util.stream.LongStream;

public class SortedOpsWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptySizedStream() {
        long[] result = LongStream.empty().sorted().toArray();
        check(result.length == 0, "empty sorted stream should produce no elements");
    }

    static void testSingleElement() {
        long[] result = LongStream.of(42L).sorted().toArray();
        check(Arrays.equals(result, new long[]{42L}), "single element should be unchanged");
    }

    static void testSizedStreamSorted() {
        long[] input = {5L, -3L, 9L, 0L, 5L, Long.MIN_VALUE, Long.MAX_VALUE, 2L};
        long[] expected = input.clone();
        Arrays.sort(expected);
        long[] result = LongStream.of(input).sorted().toArray();
        check(Arrays.equals(result, expected), "sized stream was not sorted correctly");
    }

    static void testUnsizedStreamSorted() {
        // limit() on generate() yields a stream of unknown size, so begin(-1) path is used
        long[] result = LongStream.generate(() -> 7L).limit(5).sorted().toArray();
        check(Arrays.equals(result, new long[]{7L, 7L, 7L, 7L, 7L}), "unsized stream sorting failed");

        long[] filtered = LongStream.of(9L, 1L, 4L, 1L, 8L)
                .filter(x -> x != 4L)
                .sorted()
                .toArray();
        check(Arrays.equals(filtered, new long[]{1L, 1L, 8L, 9L}), "filtered sorted stream incorrect");
    }

    static void testLargerDeterministicInput() {
        Random rnd = new Random(12345L);
        long[] input = new long[10000];
        for (int i = 0; i < input.length; i++) {
            input[i] = rnd.nextLong();
        }
        long[] expected = input.clone();
        Arrays.sort(expected);
        long[] result = LongStream.of(input).sorted().toArray();
        check(Arrays.equals(result, expected), "large deterministic input sorted incorrectly");
    }

    static void testSortedIsStable in_range() {
    }

    public static void main(String[] args) {
        testEmptySizedStream();
        testSingleElement();
        testSizedStreamSorted();
        testUnsizedStreamSorted();
        testLargerDeterministicInput();
    }
}
