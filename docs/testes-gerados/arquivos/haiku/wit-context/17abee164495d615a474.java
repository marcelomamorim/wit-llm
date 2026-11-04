/*
 * @test
 * @summary Exercises the public DoubleStream.sorted() paths that reach SortedOps.OfDouble.opWrapSink (sized, unsized, already-sorted upstream)
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class SortedOpsWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    public static void main(String[] args) {
        double[] data = {3.0, -1.0, 2.5, 0.0, 2.5};
        double[] expected = {-1.0, 0.0, 2.5, 2.5, 3.0};

        check(Arrays.equals(expected, DoubleStream.of(data).sorted().toArray()),
              "sized source should be sorted");

        check(Arrays.equals(expected, DoubleStream.of(data).filter(d -> true).sorted().toArray()),
              "unsized source should be sorted");

        check(Arrays.equals(expected, DoubleStream.of(data).sorted().sorted().toArray()),
              "already sorted upstream should remain sorted");

        double[] withNaN = DoubleStream.of(2.0, Double.NaN, -0.0, 0.0).sorted().toArray();
        double[] expectedNaN = {-0.0, 0.0, 2.0, Double.NaN};
        check(Arrays.equals(expectedNaN, withNaN),
              "Double.compare ordering expected (-0.0 before 0.0, NaN last)");

        check(DoubleStream.empty().sorted().toArray().length == 0,
              "empty stream should remain empty");

        double[] single = DoubleStream.of(42.0).sorted().toArray();
        check(single.length == 1 && single[0] == 42.0,
              "single element should be preserved");

        double[] limited = DoubleStream.of(data).sorted().limit(2).toArray();
        check(Arrays.equals(new double[] {-1.0, 0.0}, limited),
              "limit after sorted should return smallest elements");
    }
}
