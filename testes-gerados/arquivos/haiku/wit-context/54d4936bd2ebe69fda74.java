/*
 * @test
 * @summary Verify DoublePipeline.mapToInt null check and mapping behavior through public DoubleStream API
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

    static void testNullMapperThrowsNPE() {
        try {
            DoubleStream.of(1.0).mapToInt(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null mapper");
    }

    static void testMappingValues() {
        int[] result = DoubleStream.of(1.5, -2.7, 3.9)
                .mapToInt(d -> (int) d)
                .toArray();
        int[] expected = {1, -2, 3};
        if (!Arrays.equals(expected, result)) {
            throw new RuntimeException("unexpected mapping result: " + Arrays.toString(result));
        }
    }

    static void testEmptyStream() {
        int[] result = DoubleStream.empty()
                .mapToInt(d -> 1)
                .toArray();
        if (result.length != 0) {
            throw new RuntimeException("expected empty result but got length " + result.length);
        }
    }
}
