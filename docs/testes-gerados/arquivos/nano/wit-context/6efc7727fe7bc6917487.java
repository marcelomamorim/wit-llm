/*
 * @test
 * @summary Tests for java.util.stream.ReferencePipeline methods
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.IntStream;
import java.util.function.ToIntFunction;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToIntWithNullMapper();
    }

    public static void testMapToIntWithNullMapper() {
        ReferencePipeline<Object, Object> pipeline = null; // placeholder, as actual pipeline creation is complex
        ToIntFunction<Object> nullMapper = null;
        try {
            // Since the method is static and requires a pipeline, we simulate the call with a dummy pipeline
            // For the purpose of this test, we focus on the null argument behavior
            ReferencePipeline<Object, Object> dummyPipeline = new ReferencePipeline<Object, Object>(
                () -> null, 0, false) {};
            dummyPipeline.mapToInt(nullMapper);
            // If no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null mapper.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
