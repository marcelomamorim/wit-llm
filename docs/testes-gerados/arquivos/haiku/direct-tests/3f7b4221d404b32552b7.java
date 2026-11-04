/*
 * @test
 * @summary Exercises Nodes sized collection (begin/sink path) through public parallel toArray APIs
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NodesWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testParallelSizedIntToArray() {
        final int n = 10000;
        int[] actual = IntStream.range(0, n).parallel().toArray();
        int[] expected = new int[n];
        for (int i = 0; i < n; i++) {
            expected[i] = i;
        }
        check(Arrays.equals(expected, actual), "parallel IntStream.toArray content/order mismatch");
    }

    static void testParallelSizedRefToArray() {
        Integer[] actual = Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
                .parallel()
                .toArray(Integer[]::new);
        Integer[] expected = {1, 2, 3, 4, 5, 6, 7, 8};
        check(Arrays.equals(expected, actual), "parallel Stream.toArray(generator) mismatch");
    }

    static void testSequentialRefToArray() {
        Object[] actual = Stream.of("a", "b", "c").toArray();
        check(actual.length == 3, "sequential toArray length mismatch");
        check("a".equals(actual[0]) && "b".equals(actual[1]) && "c".equals(actual[2]),
              "sequential toArray content mismatch");
    }

    static void testEmptyParallelToArray() {
        Object[] actual = Stream.empty().parallel().toArray();
        check(actual.length == 0, "empty parallel toArray should have length 0");

        int[] ints = IntStream.empty().parallel().toArray();
        check(ints.length == 0, "empty parallel IntStream.toArray should have length 0");
    }

    static void testUnsizedParallelToArray() {
        final int n = 5000;
        int[] actual = IntStream.range(0, 2 * n)
                .filter(x -> x % 2 == 0)
                .parallel()
                .toArray();
        check(actual.length == n, "filtered parallel toArray length mismatch: " + actual.length);
        for (int i = 0; i < n; i++) {
            check(actual[i] == 2 * i, "filtered parallel toArray order mismatch at " + i);
        }
    }

    static void testLongAndDoubleParallelToArray() {
        long[] longs = java.util.stream.LongStream.rangeClosed(1, 4096).parallel().toArray();
        check(longs.length == 4096, "LongStream parallel toArray length mismatch");
        check(longs[0] == 1L && longs[4095] == 4096L, "LongStream parallel toArray content mismatch");

        double[] doubles = java.util.stream.DoubleStream.of(0.5, 1.5, 2.5).parallel().toArray();
        check(Arrays.equals(doubles, new double[] {0.5, 1.5, 2.5}),
              "DoubleStream parallel toArray content mismatch");
    }

    public static void main(String[] args) {
        testParallelSizedIntToArray();
        testParallelSizedRefToArray();
        testSequentialRefToArray();
        testEmptyParallelToArray();
        testUnsizedParallelToArray();
        testLongAndDoubleParallelToArray();
    }
}
