/*
 * @test
 * @summary Verifies DoublePipeline.noneMatch through the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        check(DoubleStream.empty().noneMatch(d -> true), true, "empty stream");
        check(DoubleStream.empty().noneMatch(d -> false), true, "empty stream, always false");
        check(DoubleStream.of(1.0, 2.0).noneMatch(d -> d > 5.0), true, "no element matches");
        check(DoubleStream.of(1.0, 2.0, 3.0).noneMatch(d -> d == 2.0), false, "one element matches");
        check(DoubleStream.of(1.0, 2.0).noneMatch(d -> true), false, "all elements match");
        check(DoubleStream.of(Double.NaN).noneMatch(Double::isNaN), false, "NaN matches");
        check(DoubleStream.of(1.0, Double.NaN).noneMatch(Double::isNaN), false, "NaN among values");
        check(DoubleStream.of(0.0).noneMatch(d -> d == -0.0), false, "zero equals negative zero by ==");

        int[] calls = {0};
        boolean result = DoubleStream.of(1.0, 2.0, 3.0).noneMatch(d -> {
            calls[0]++;
            return d == 1.0;
        });
        check(result, false, "short-circuit result");
        check(calls[0] == 1, true, "short-circuit stops after first match");
    }

    private static void check(boolean actual, boolean expected, String message) {
        if (actual != expected) {
            throw new RuntimeException("Failed: " + message + " expected=" + expected + " actual=" + actual);
        }
    }
}
