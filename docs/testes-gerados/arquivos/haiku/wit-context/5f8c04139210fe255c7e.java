/*
 * @test
 * @summary Witup-derived checks for DoubleStream.mapToLong (DoublePipeline) via public API
 * @run main DoublePipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testMappingValues();
        testEmptyStream();
    }

    private static void testNullMapperThrowsNPE() {
        boolean thrown = false;
        try {
            DoubleStream.of(1.0).mapToLong(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null mapper");
        }
    }

    private static void testMappingValues() {
        long[] actual = DoubleStream.of(1.5, 2.9, -3.7)
                .mapToLong(d -> (long) d)
                .toArray();
        long[] expected = {1L, 2L, -3L};
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected mapping result: " + Arrays.toString(actual));
        }
    }

    private static void testEmptyStream() {
        long count = DoubleStream.empty()
                .mapToLong(d -> (long) d)
                .count();
        if (count != 0L) {
            throw new RuntimeException("Expected empty result, got count " + count);
        }
    }
}
