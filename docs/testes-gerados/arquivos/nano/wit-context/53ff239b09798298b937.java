/*
 * @test
 * @summary Tests the forEach method of IntPipeline for correct behavior and exception handling.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachWithNonParallel();
        testForEachWithParallel();
    }

    private static void testForEachWithNonParallel() {
        // Create a simple IntPipeline with a known source
        IntPipeline.Head pipeline = new IntPipeline.Head(
            () -> java.util.Spliterators.spliterator(new int[]{1, 2, 3}, 0),
            0,
            false
        );
        final StringBuilder result = new StringBuilder();
        IntConsumer consumer = value -> result.append(value).append(",");
        try {
            pipeline.forEach(consumer);
            String output = result.toString();
            if (!output.equals("1,2,3,")) {
                throw new AssertionError("Unexpected output: " + output);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in non-parallel forEach", e);
        }
    }

    private static void testForEachWithParallel() {
        // Create a pipeline and invoke forEach in parallel mode
        IntPipeline pipeline = new IntPipeline.Head(
            () -> java.util.Spliterators.spliterator(new int[]{4, 5, 6}, 0),
            0,
            true
        );
        final StringBuilder result = new StringBuilder();
        IntConsumer consumer = value -> {
            synchronized (result) {
                result.append(value).append(",");
            }
        };
        try {
            pipeline.forEach(consumer);
            String output = result.toString();
            if (!(output.contains("4,") && output.contains("5,") && output.contains("6,"))) {
                throw new AssertionError("Unexpected output in parallel: " + output);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in parallel forEach", e);
        }
    }
}
