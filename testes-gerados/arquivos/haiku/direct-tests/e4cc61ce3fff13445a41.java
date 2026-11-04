/*
 * @test
 * @summary Verifies double array results obtained through public DoubleStream APIs that rely on Nodes builders and asPrimitiveArray.
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class NodesWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkArray(double[] actual, double[] expected, String name) {
        check(Arrays.equals(actual, expected),
              name + ": expected " + Arrays.toString(expected)
              + " but was " + Arrays.toString(actual));
    }

    public static void main(String[] args) {
        checkArray(DoubleStream.of(1.0, 2.5, 3.0).toArray(),
                   new double[] {1.0, 2.5, 3.0}, "simple");

        checkArray(DoubleStream.empty().toArray(),
                   new double[0], "empty");

        checkArray(DoubleStream.of(1.0, 2.0)
                       .flatMap(x -> DoubleStream.of(x, x * 10))
                       .toArray(),
                   new double[] {1.0, 10.0, 2.0, 20.0}, "flatMap");

        checkArray(DoubleStream.concat(DoubleStream.of(1.0),
                                       DoubleStream.of(2.0, 3.0))
                       .toArray(),
                   new double[] {1.0, 2.0, 3.0}, "concat");

        checkArray(DoubleStream.of(-0.0, Double.NaN, Double.MAX_VALUE)
                       .toArray(),
                   new double[] {-0.0, Double.NaN, Double.MAX_VALUE}, "special values");
    }
}
