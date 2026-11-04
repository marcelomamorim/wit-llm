/*
 * @test
 * @summary Smoke test reaching java.util.stream.Nodes through public DoubleStream API
 * @run main NodesWitupTest
 */

import java.util.stream.DoubleStream;

public class NodesWitupTest {

    public static void main(String[] args) {
        double[] empty = DoubleStream.empty().toArray();
        if (empty.length != 0) {
            throw new RuntimeException("expected empty array but length was " + empty.length);
        }

        double[] values = DoubleStream.of(1.0, 2.0, 3.0).toArray();
        if (values.length != 3
                || values[0] != 1.0
                || values[1] != 2.0
                || values[2] != 3.0) {
            throw new RuntimeException("unexpected array contents");
        }
    }
}
