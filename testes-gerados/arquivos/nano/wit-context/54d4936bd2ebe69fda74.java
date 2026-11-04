/*
 * @test
 * @summary Tests for DoublePipeline.mapToInt method, focusing on null input handling.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.IntStream;
import java.util.function.DoubleToIntFunction;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapToIntWithNullMapper();
    }

    public static void testMapToIntWithNullMapper() {
        DoublePipeline pipeline = createSamplePipeline();
        try {
            pipeline.mapToInt(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null mapper.");
        }
    }

    private static DoublePipeline createSamplePipeline() {
        // Creating a minimal DoublePipeline instance for testing purposes.
        // Since DoublePipeline is abstract, we need a concrete subclass or a mock.
        // For simplicity, we can create an anonymous subclass with minimal implementation.
        return new DoublePipeline(null, 0, false) {
            @Override
            public IntStream mapToInt(DoubleToIntFunction mapper) {
                return super.mapToInt(mapper);
            }
        };
    }
}
