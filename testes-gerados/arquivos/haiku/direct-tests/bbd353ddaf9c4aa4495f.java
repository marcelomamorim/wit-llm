/*
 * @test
 * @summary Verifies primitive array materialization of streams through public toArray() APIs
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class NodesWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        int[] ints = IntStream.range(0, 5).toArray();
        check(Arrays.equals(ints, new int[] {0, 1, 2, 3, 4}), "int range toArray mismatch");

        int[] emptyInts = IntStream.empty().toArray();
        check(emptyInts.length == 0, "empty int stream should produce empty array");

        long[] longs = LongStream.rangeClosed(1, 3).toArray();
        check(Arrays.equals(longs, new long[] {1L, 2L, 3L}), "long range toArray mismatch");

        long[] emptyLongs = LongStream.empty().toArray();
        check(emptyLongs.length == 0, "empty long stream should produce empty array");

        double[] doubles = DoubleStream.of(1.5, 2.5).toArray();
        check(Arrays.equals(doubles, new double[] {1.5, 2.5}), "double toArray mismatch");

        double[] emptyDoubles = DoubleStream.empty().toArray();
        check(emptyDoubles.length == 0, "empty double stream should produce empty array");

        int[] flat = IntStream.range(0, 3)
                .flatMap(i -> IntStream.of(i, i * 10))
                .toArray();
        check(Arrays.equals(flat, new int[] {0, 0, 1, 10, 2, 20}), "flatMap int toArray mismatch");

        int[] large = IntStream.range(0, 10000).toArray();
        check(large.length == 10000, "large int toArray length mismatch");
        check(large[0] == 0 && large[9999] == 9999, "large int toArray content mismatch");
    }
}
