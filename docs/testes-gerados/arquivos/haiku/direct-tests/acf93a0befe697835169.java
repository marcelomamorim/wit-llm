/*
 * @test
 * @summary Tests ReferencePipeline.mapToDouble through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapsValuesInOrder();
        testEmptyStream();
        testNullMapperThrowsNPE();
        testMapperIsLazy();
        testResultIsDoubleStream();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMapsValuesInOrder() {
        double[] result = Stream.of("a", "bb", "ccc")
                .mapToDouble(String::length)
                .toArray();
        check(Arrays.equals(result, new double[] {1.0, 2.0, 3.0}),
              "unexpected mapping result: " + Arrays.toString(result));
    }

    static void testEmptyStream() {
        double[] result = Stream.<String>empty()
                .mapToDouble(String::length)
                .toArray();
        check(result.length == 0, "expected empty result");
    }

    static void testNullMapperThrowsNPE() {
        try {
            Stream.of("a").mapToDouble(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null mapper");
    }

    static void testMapperIsLazy() {
        int[] calls = {0};
        DoubleStream ds = Stream.of("a", "bb", "ccc")
                .mapToDouble(s -> {
                    calls[0]++;
                    return s.length();
                });
        check(calls[0] == 0, "mapper invoked before terminal operation");
        double sum = ds.sum();
        check(calls[0] == 3, "mapper invoked " + calls[0] + " times, expected 3");
        check(sum == 6.0, "unexpected sum: " + sum);
    }

    static void testResultIsDoubleStream() {
        DoubleStream ds = Stream.of("x").mapToDouble(s -> 0.5);
        double[] result = ds.toArray();
        check(Arrays.equals(result, new double[] {0.5}),
              "unexpected value: " + Arrays.toString(result));
    }
}
