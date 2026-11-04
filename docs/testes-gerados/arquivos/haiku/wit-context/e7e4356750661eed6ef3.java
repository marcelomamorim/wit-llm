/*
 * @test
 * @summary Public-API behavior of DoublePipeline mapToLong stage (anonymous Sink accept path)
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        long[] r = DoubleStream.of(1.5, -2.7, 3.0)
                .mapToLong(d -> (long) d)
                .toArray();
        check(r.length == 3, "length");
        check(r[0] == 1L, "1.5 -> 1");
        check(r[1] == -2L, "-2.7 -> -2");
        check(r[2] == 3L, "3.0 -> 3");

        long[] composed = DoubleStream.of(0.9, 2.1)
                .map(x -> x * 2)
                .mapToLong(d -> (long) d)
                .toArray();
        check(composed.length == 2, "composed length");
        check(composed[0] == 1L, "0.9*2 -> 1");
        check(composed[1] == 4L, "2.1*2 -> 4");

        long[] empty = DoubleStream.empty()
                .mapToLong(d -> (long) d)
                .toArray();
        check(empty.length == 0, "empty stream");

        boolean thrown = false;
        try {
            DoubleStream.of(1.0)
                    .mapToLong(d -> { throw new IllegalStateException("mapper"); })
                    .toArray();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "mapper exception must propagate");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
