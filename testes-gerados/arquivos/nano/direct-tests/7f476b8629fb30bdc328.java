/*
 * @test
 * @summary Test for LongPipeline.forEachOrdered method to ensure it executes the action on each element in order.
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongStream;
import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testForEachOrderedExecutesAction();
    }

    public static void testForEachOrderedExecutesAction() {
        long[] data = {1L, 2L, 3L, 4L, 5L};
        StringBuilder result = new StringBuilder();
        LongConsumer action = value -> result.append(value).append(",");
        LongStream stream = LongStream.of(data);
        // Wrap stream into a pipeline and invoke forEachOrdered
        stream.forEachOrdered(action);
        // Verify that the result contains all elements in order
        String expected = "1,2,3,4,5,";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + " but got: " + result.toString());
        }
    }
}
