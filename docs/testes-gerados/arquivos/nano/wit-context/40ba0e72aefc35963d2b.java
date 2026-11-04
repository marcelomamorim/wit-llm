/*
 * @test
 * @summary Tests for IntPipeline methods, focusing on exception paths and behavior.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.LongStream;
import java.util.function.IntToLongFunction;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMapToLongWithNullMapper();
    }

    public static void testMapToLongWithNullMapper() {
        IntPipeline pipeline = createSamplePipeline();
        try {
            pipeline.mapToLong(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes.
            System.out.println("NullPointerException correctly thrown for null mapper.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }

    private static IntPipeline createSamplePipeline() {
        // Create a simple pipeline with a small array of integers.
        int[] data = {1, 2, 3};
        return IntPipeline.of(data, 0, data.length, false);
    }
}
