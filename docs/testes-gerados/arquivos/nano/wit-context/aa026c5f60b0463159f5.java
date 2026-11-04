/*
 * @test
 * @summary Test for LongPipeline.findAny() method to observe behavior and exception handling.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.OptionalLong;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyWithLinkedOrConsumed();
        testFindAnyNormal();
    }

    private static void testFindAnyWithLinkedOrConsumed() {
        LongPipeline pipeline = createSamplePipeline();
        // Simulate linkedOrConsumed state by consuming the pipeline
        pipeline.forEach(System.out::println);
        try {
            OptionalLong result = pipeline.findAny();
            System.out.println("findAny() result after consumption: " + result);
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException when linkedOrConsumed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    private static void testFindAnyNormal() {
        LongPipeline pipeline = createSamplePipeline();
        try {
            OptionalLong result = pipeline.findAny();
            System.out.println("findAny() normal result: " + result);
        } catch (Exception e) {
            System.out.println("Unexpected exception in normal case: " + e);
        }
    }

    private static LongPipeline createSamplePipeline() {
        // Create a simple pipeline with a small array
        long[] data = {1L, 2L, 3L};
        return LongPipeline.from(java.util.Arrays.stream(data).spliterator(), false);
    }
}
