/*
 * @test    @summary Test for IntPipeline.findAny() method.
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyWithElements();
        testFindAnyWithEmpty();
    }

    static void testFindAnyWithElements() {
        IntPipeline pipeline = IntPipeline.of(new int[]{1, 2, 3, 4, 5});
        OptionalInt result = pipeline.findAny();
        if (!result.isPresent()) {
            throw new AssertionError("Expected present OptionalInt");
        }
        int value = result.getAsInt();
        if (value < 1 || value > 5) {
            throw new AssertionError("Value out of expected range: " + value);
        }
    }

    static void testFindAnyWithEmpty() {
        IntPipeline emptyPipeline = IntPipeline.of(new int[] {});
        OptionalInt result = emptyPipeline.findAny();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt");
        }
    }
}
