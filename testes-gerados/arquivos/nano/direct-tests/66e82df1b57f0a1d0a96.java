/*
 * @test    @summary Test for IntPipeline.forEach method to ensure it applies the action to each element.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachAppliesAction();
    }

    public static void testForEachAppliesAction() {
        final StringBuilder result = new StringBuilder();
        IntConsumer consumer = (int value) -> result.append(value).append(",");
        IntPipeline.of(new int[]{1, 2, 3, 4, 5})
                   .forEach(consumer);
        String expected = "1,2,3,4,5,";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + " but got: " + result.toString());
        }
    }
}
