/*
 * @test
 * @summary Behavioral tests for DoublePipeline.flatMap via public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapBasic();
        testFlatMapEmptyInnerStreams();
        testFlatMapNullResultIsSkipped();
        testFlatMapNullMapperThrowsNPE();
        testFlatMapShortCircuitWithLimit();
        testFlatMapClosesInnerStreams();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFlatMapBasic() {
        double[] result = DoubleStream.of(1.0, 2.0, 3.0)
                .flatMap(x -> DoubleStream.of(x, x * 10))
                .toArray();
        double[] expected = {1.0, 10.0, 2.0, 20.0, 3.0, 30.0};
        check(Arrays.equals(expected, result),
              "flatMap basic: unexpected " + Arrays.toString(result));
    }

    static void testFlatMapEmptyInnerStreams() {
        double[] result = DoubleStream.of(1.0, 2.0, 3.0)
                .flatMap(x -> x == 2.0 ? DoubleStream.empty() : DoubleStream.of(x))
                .toArray();
        double[] expected = {1.0, 3.0};
        check(Arrays.equals(expected, result),
              "flatMap empty inner: unexpected " + Arrays.toString(result));
    }

    static void testFlatMapNullResultIsSkipped() {
        double[] result = DoubleStream.of(1.0, 2.0, 3.0)
                .flatMap(x -> x == 2.0 ? null : DoubleStream.of(x))
                .toArray();
        double[] expected = {1.0, 3.0};
        check(Arrays.equals(expected, result),
              "flatMap null result: unexpected " + Arrays.toString(result));
    }

    static void testFlatMapNullMapperThrowsNPE() {
        boolean thrown = false;
        try {
            DoubleStream.of(1.0).flatMap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "flatMap(null) should throw NullPointerException");
    }

    static void testFlatMapShortCircuitWithLimit() {
        double[] result = DoubleStream.of(1.0, 2.0, 3.0)
                .flatMap(x -> DoubleStream.of(x, x + 10))
                .limit(3)
                .toArray();
        double[] expected = {1.0, 11.0, 2.0};
        check(Arrays.equals(expected, result),
              "flatMap with limit: unexpected " + Arrays.toString(result));
    }

    static void testFlatMapClosesInnerStreams() {
        int[] closes = new int[1];
        double sum = DoubleStream.of(1.0, 2.0)
                .flatMap(x -> DoubleStream.of(x).onClose(() -> closes[0]++))
                .sum();
        check(sum == 3.0, "flatMap close test: unexpected sum " + sum);
        check(closes[0] == 2, "flatMap should close each inner stream, closes=" + closes[0]);
    }
}
