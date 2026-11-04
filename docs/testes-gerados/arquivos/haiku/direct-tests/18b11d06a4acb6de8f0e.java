/*
 * @test
 * @summary Behavioral checks for DoublePipeline.anyMatch via the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        check(DoubleStream.of(1.0, 2.0, 3.0).anyMatch(d -> d == 2.0),
              "expected true when one element matches");

        check(!DoubleStream.of(1.0, 2.0, 3.0).anyMatch(d -> d > 10.0),
              "expected false when no element matches");

        check(!DoubleStream.empty().anyMatch(d -> true),
              "expected false for empty stream");

        check(DoubleStream.of(Double.NaN).anyMatch(Double::isNaN),
              "expected true for NaN element matched by Double::isNaN");

        int[] calls = {0};
        boolean r = DoubleStream.of(1.0, 2.0, 3.0).anyMatch(d -> {
            calls[0]++;
            return d == 1.0;
        });
        check(r, "expected true on first element match");
        check(calls[0] == 1, "expected short-circuit after first match, calls=" + calls[0]);

        int[] calls2 = {0};
        boolean r2 = DoubleStream.of(1.0, 2.0).anyMatch(d -> {
            calls2[0]++;
            return false;
        });
        check(!r2, "expected false when predicate never matches");
        check(calls2[0] == 2, "expected predicate evaluated for every element, calls=" + calls2[0]);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
