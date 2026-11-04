/*
 * @test
 * @summary Testa o método min() de IntPipeline para comportamento observável.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.OptionalInt;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMinWithEmptyStream();
        testMinWithValues();
    }

    private static void testMinWithEmptyStream() {
        IntPipeline head = createEmptyIntPipeline();
        OptionalInt result = head.min();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt for empty stream, but got: " + result);
        }
    }

    private static void testMinWithValues() {
        IntPipeline pipeline = createPipelineWithValues(new int[] {5, 3, 8, 2, 7});
        OptionalInt result = pipeline.min();
        if (!result.isPresent()) {
            throw new AssertionError("Expected non-empty OptionalInt with values, but got empty.");
        }
        if (result.getAsInt() != 2) {
            throw new AssertionError("Expected min to be 2, but got: " + result.getAsInt());
        }
    }

    private static IntPipeline createEmptyIntPipeline() {
        return IntPipeline.of(new int[0], false);
    }

    private static IntPipeline createPipelineWithValues(int[] values) {
        return IntPipeline.of(values, false);
    }
}
