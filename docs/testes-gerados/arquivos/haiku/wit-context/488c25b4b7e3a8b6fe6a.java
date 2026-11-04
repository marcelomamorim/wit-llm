/*
 * @test
 * @summary Checks that parallel sized DoubleStream.toArray() (which reaches Nodes sized double collection) preserves all elements and order
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class NodesWitupTest {

    public static void main(String[] args) {
        final int n = 1000;
        double[] input = new double[n];
        double[] expected = new double[n];
        for (int i = 0; i < n; i++) {
            input[i] = i;
            expected[i] = i * 2.0;
        }
        double[] actual = DoubleStream.of(input).parallel().map(x -> x * 2.0).toArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Parallel sized toArray lost or reordered elements");
        }

        double[] empty = DoubleStream.of(new double[0]).parallel().toArray();
        if (empty.length != 0) {
            throw new RuntimeException("Empty parallel toArray must return an empty array");
        }
    }
}
