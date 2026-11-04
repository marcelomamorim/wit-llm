/*
 * @test
 * @summary Testa o método sum() de IntPipeline para comportamento padrão.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSumNormalCase();
        testSumEmptyStream();
        testSumWithNegativeValues();
    }

    private static void testSumNormalCase() {
        int[] data = {1, 2, 3, 4, 5};
        int result = IntPipeline.of(data, 0, data.length).sum();
        if (result != 15) {
            throw new AssertionError("Expected sum 15, but got " + result);
        }
    }

    private static void testSumEmptyStream() {
        int[] empty = {};
        int result = IntPipeline.of(empty, 0, 0).sum();
        if (result != 0) {
            throw new AssertionError("Expected sum 0 for empty stream, but got " + result);
        }
    }

    private static void testSumWithNegativeValues() {
        int[] data = {-1, -2, -3};
        int result = IntPipeline.of(data, 0, data.length).sum();
        if (result != -6) {
            throw new AssertionError("Expected sum -6, but got " + result);
        }
    }
}
