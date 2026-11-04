/*
 * @test
 * @summary Checks that LongPipeline.mapToDouble stage (anonymous Sink.accept(long)) converts each long element to double downstream
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testConversionOfSmallValues();
        testConversionOfExtremeValues();
        testConversionPreservesOrder();
        testEmptyStream();
    }

    static void testConversionOfSmallValues() {
        double[] result = LongStream.of(1L, 2L, 3L)
                .mapToDouble(l -> l * 0.5)
                .toArray();
        double[] expected = {0.5, 1.0, 1.5};
        if (!Arrays.equals(expected, result)) {
            throw new RuntimeException("unexpected values: " + Arrays.toString(result));
        }
    }

    static void testConversionOfExtremeValues() {
        long[] input = {Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE};
        double[] result = LongStream.of(input)
                .mapToDouble(l -> l)
                .toArray();
        for (int i = 0; i < input.length; i++) {
            double expected = (double) input[i];
            if (Double.compare(expected, result[i]) != 0) {
                throw new RuntimeException("index " + i + ": expected " + expected
                        + " but was " + result[i]);
            }
        }
    }

    static void testConversionPreservesOrder() {
        double[] result = LongStream.rangeClosed(1L, 5L)
                .mapToDouble(l -> (double) l * 10.0)
                .toArray();
        double[] expected = {10.0, 20.0, 30.0, 40.0, 50.0};
        if (!Arrays.equals(expected, result)) {
            throw new RuntimeException("order not preserved: " + Arrays.toString(result));
        }
    }

    static void testEmptyStream() {
        double[] result = LongStream.empty()
                .mapToDouble(l -> l)
                .toArray();
        if (result.length != 0) {
            throw new RuntimeException("expected empty array but was length " + result.length);
        }
    }
}
