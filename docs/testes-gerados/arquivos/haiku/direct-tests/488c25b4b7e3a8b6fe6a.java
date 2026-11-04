/*
 * @test
 * @summary Exercises Nodes double builder accept path through public DoubleStream.toArray
 * @run main NodesWitupTest
 */

import java.util.Arrays;

public class NodesWitupTest {

    public static void main(String[] args) {
        double[] input = {1.5, -2.0, 0.0, Double.NaN, 3.25};
        double[] out = Arrays.stream(input).toArray();
        if (!Arrays.equals(input, out)) {
            throw new RuntimeException("toArray content mismatch: " + Arrays.toString(out));
        }

        double[] empty = Arrays.stream(new double[0]).toArray();
        if (empty.length != 0) {
            throw new RuntimeException("expected empty array, got length " + empty.length);
        }

        double[] single = Arrays.stream(new double[]{Double.MIN_VALUE}).toArray();
        if (single.length != 1 || single[0] != Double.MIN_VALUE) {
            throw new RuntimeException("single element mismatch");
        }

        double[] negZero = Arrays.stream(new double[]{-0.0}).toArray();
        if (Double.compare(negZero[0], -0.0) != 0) {
            throw new RuntimeException("negative zero not preserved");
        }
    }
}
