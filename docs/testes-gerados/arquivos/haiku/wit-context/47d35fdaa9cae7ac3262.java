/*
 * @test
 * @summary Observable DoubleStream behavior that exercises the double node builders in Nodes
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class NodesWitupTest {
    public static void main(String[] args) {
        double[] expected = {1.0, 2.0, 3.0};
        double[] actual = DoubleStream.of(1.0, 2.0, 3.0).toArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected array: " + Arrays.toString(actual));
        }

        double[] mapped = DoubleStream.of(expected).map(x -> x * 2).toArray();
        double[] mappedExpected = {2.0, 4.0, 6.0};
        if (!Arrays.equals(mappedExpected, mapped)) {
            throw new RuntimeException("Unexpected mapped array: " + Arrays.toString(mapped));
        }

        double[] empty = DoubleStream.empty().toArray();
        if (empty.length != 0) {
            throw new RuntimeException("Expected empty array but length was " + empty.length);
        }
    }
}
