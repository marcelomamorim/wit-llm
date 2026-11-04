/*
 * @test
 * @summary Verifies observable sorting behavior of DoubleStream.sorted(), which appends SortedOps.makeDouble and calls OfDouble.begin(size)
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        double[] input = {3.5, -1.0, 2.25, 0.0, 10.0};
        double[] expected = input.clone();
        Arrays.sort(expected);

        double[] actual = DoubleStream.of(input).sorted().toArray();
        check(Arrays.equals(expected, actual), "sorted() of sized stream: " + Arrays.toString(actual));

        double[] sizedFromArray = Arrays.stream(input).sorted().toArray();
        check(Arrays.equals(expected, sizedFromArray), "sorted() of Arrays.stream: " + Arrays.toString(sizedFromArray));

        double[] empty = DoubleStream.empty().sorted().toArray();
        check(empty.length == 0, "sorted() of empty stream must be empty");

        double[] withNaN = DoubleStream.of(Double.NaN, 1.0, -2.0).sorted().toArray();
        double[] expectedNaN = {-2.0, 1.0, Double.NaN};
        check(Arrays.equals(expectedNaN, withNaN), "NaN must sort last: " + Arrays.toString(withNaN));

        double[] filtered = DoubleStream.of(input).filter(d -> d > 0).sorted().toArray();
        double[] expectedFiltered = {2.25, 3.5, 10.0};
        check(Arrays.equals(expectedFiltered, filtered), "sorted() after filter: " + Arrays.toString(filtered));

        double[] sortedTwice = DoubleStream.of(input).sorted().sorted().toArray();
        check(Arrays.equals(expected, sortedTwice), "double sorted() must be idempotent");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
