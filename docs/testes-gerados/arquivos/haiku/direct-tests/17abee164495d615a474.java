/*
 * @test
 * @summary Verifies observable sorting behavior of DoubleStream.sorted(), which reaches SortedOps.opWrapSink for sized, unsized and already-sorted upstreams
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        double[] input = {3.0, -1.0, 2.5, 0.0, 2.5};
        double[] expected = {-1.0, 0.0, 2.5, 2.5, 3.0};

        // Sized upstream: SIZED flag known -> SizedDoubleSortingSink path
        check("sized", Arrays.equals(expected, DoubleStream.of(input).sorted().toArray()));

        // Unsized upstream: no SIZED flag -> DoubleSortingSink path
        check("unsized", Arrays.equals(expected,
                DoubleStream.of(input).filter(d -> true).sorted().toArray()));

        // Already sorted upstream: SORTED flag known -> sink returned unchanged
        check("already-sorted", Arrays.equals(expected,
                DoubleStream.of(expected).sorted().toArray()));

        // Sorting twice must yield the same ordering
        check("double-sorted", Arrays.equals(expected,
                DoubleStream.of(input).sorted().sorted().toArray()));

        // Empty stream
        check("empty", DoubleStream.empty().sorted().toArray().length == 0);

        // NaN ordering follows Arrays.sort(double[]) semantics: NaN placed last
        double[] withNaN = {Double.NaN, 1.0, -2.0};
        double[] expectedNaN = {-2.0, 1.0, Double.NaN};
        check("nan-last", Arrays.equals(expectedNaN,
                DoubleStream.of(withNaN).sorted().toArray()));
    }

    private static void check(String name, boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed: " + name);
        }
    }
}
