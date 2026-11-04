/*
 * @test
 * @summary Test for IntPipeline.forEachOrdered method to observe behavior with valid and null inputs.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedWithValidConsumer();
        testForEachOrderedWithNullConsumer();
    }

    private static void testForEachOrderedWithValidConsumer() {
        int[] array = {1, 2, 3};
        StringBuilder sb = new StringBuilder();
        IntConsumer consumer = (i) -> sb.append(i).append(",");
        try {
            // Create a pipeline from array
            IntPipeline pipeline = IntPipeline.of(array, 0, array.length, false);
            // Call forEachOrdered
            pipeline.forEachOrdered(consumer);
            // Verify the consumer was called for each element
            String result = sb.toString();
            if (!result.equals("1,2,3,")) {
                throw new AssertionError("Unexpected output: " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in valid consumer test", t);
        }
    }

    private static void testForEachOrderedWithNullConsumer() {
        int[] array = {4, 5, 6};
        try {
            IntPipeline pipeline = IntPipeline.of(array, 0, array.length, false);
            pipeline.forEachOrdered(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }
}
