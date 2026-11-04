/*
 * @test
 * @summary Test for DoublePipeline.mapToLong method to ensure correct mapping behavior.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.function.DoubleToLongFunction;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapToLongWithPositiveValues();
        testMapToLongWithNegativeValues();
        testMapToLongWithZero();
        testMapToLongWithNullMapper();
    }

    static void testMapToLongWithPositiveValues() {
        DoubleStream ds = DoubleStream.of(1.0, 2.0, 3.0);
        DoubleToLongFunction mapper = d -> (long) (d * 10);
        LongStream result = ds.mapToLong(mapper);
        long[] expected = {10L, 20L, 30L};
        long[] actual = result.toArray();
        assert actual.length == expected.length : "Length mismatch";
        for (int i = 0; i < expected.length; i++) {
            assert actual[i] == expected[i] : "Mismatch at index " + i;
        }
    }

    static void testMapToLongWithNegativeValues() {
        DoubleStream ds = DoubleStream.of(-1.0, -2.0, -3.0);
        DoubleToLongFunction mapper = d -> (long) (d * 5);
        LongStream result = ds.mapToLong(mapper);
        long[] expected = {-5L, -10L, -15L};
        long[] actual = result.toArray();
        assert actual.length == expected.length : "Length mismatch";
        for (int i = 0; i < expected.length; i++) {
            assert actual[i] == expected[i] : "Mismatch at index " + i;
        }
    }

    static void testMapToLongWithZero() {
        DoubleStream ds = DoubleStream.of(0.0, 0.0, 0.0);
        DoubleToLongFunction mapper = d -> (long) d;
        LongStream result = ds.mapToLong(mapper);
        long[] expected = {0L, 0L, 0L};
        long[] actual = result.toArray();
        assert actual.length == expected.length : "Length mismatch";
        for (int i = 0; i < expected.length; i++) {
            assert actual[i] == expected[i] : "Mismatch at index " + i;
        }
    }

    static void testMapToLongWithNullMapper() {
        DoubleStream ds = DoubleStream.of(1.0, 2.0);
        try {
            ds.mapToLong(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
