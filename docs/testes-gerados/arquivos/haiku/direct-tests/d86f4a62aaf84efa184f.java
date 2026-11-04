/*
 * @test
 * @summary Checks observable ordering of DoubleStream.sorted(), which exercises SortedOps.OfDouble.begin(long) indirectly
 * @run main SortedOpsWitupTest
 */

import java.util.stream.DoubleStream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        double[] sorted = DoubleStream.of(3.0, Double.NaN, -0.0, 0.0, 1.0).sorted().toArray();
        check(sorted, new double[] {-0.0, 0.0, 1.0, 3.0, Double.NaN});

        double[] empty = DoubleStream.empty().sorted().toArray();
        check(empty, new double[0]);

        double[] unsized = DoubleStream.iterate(5.0, d -> d - 1).limit(5).sorted().toArray();
        check(unsized, new double[] {1.0, 2.0, 3.0, 4.0, 5.0});

        double[] input = new double[1000];
        for (int i = 0; i < input.length; i++) {
            input[i] = input.length - i;
        }
        double[] big = DoubleStream.of(input).sorted().toArray();
        if (big.length != 1000 || big[0] != 1.0 || big[999] != 1000.0) {
            throw new RuntimeException("unexpected sorted result for sized input");
        }
    }

    static void check(double[] actual, double[] expected) {
        if (actual.length != expected.length) {
            throw new RuntimeException("length mismatch: " + actual.length + " vs " + expected.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (Double.doubleToLongBits(actual[i]) != Double.doubleToLongBits(expected[i])) {
                throw new RuntimeException("mismatch at index " + i + ": " + actual[i] + " vs " + expected[i]);
            }
        }
    }
}
