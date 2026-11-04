/*
 * @test
 * @summary Tests for java.util.stream.ReferencePipeline methods
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.DoubleStream;
import java.util.function.ToDoubleFunction;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToDoubleWithNullMapper();
    }

    public static void testMapToDoubleWithNullMapper() {
        ReferencePipeline<Object, Object> pipeline = null; // placeholder, as actual pipeline creation depends on context
        ToDoubleFunction<Object> nullMapper = null;
        try {
            // Since the method is static in the class, we simulate calling it with null argument
            DoubleStream result = ReferencePipeline.mapToDouble(null, nullMapper);
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
