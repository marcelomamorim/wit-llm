/*
 * @test
 * @summary Public behavior of DoubleStream.sorted(), which reaches SortedOps.SizedDoubleSortingSink.begin for sized streams
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        double[] input = {3.0, -1.0, 2.5, 0.0};
        double[] expected = {-1.0, 0.0, 2.5, 3.0};
        double[] actual = DoubleStream.of(input).sorted().toArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("sorted() returned " + Arrays.toString(actual));
        }

        double[] empty = DoubleStream.of().sorted().toArray();
        if (empty.length != 0) {
            throw new RuntimeException("sorted() on empty stream returned length " + empty.length);
        }

        double[] single = DoubleStream.of(42.0).sorted().toArray();
        if (!Arrays.equals(new double[] {42.0}, single)) {
            throw new RuntimeException("sorted() on single element returned " + Arrays.toString(single));
        }
    }
}
