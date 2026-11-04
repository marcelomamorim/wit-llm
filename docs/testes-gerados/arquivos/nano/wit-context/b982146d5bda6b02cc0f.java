/*
 * @test
 * @summary Tests for IntPipeline.flatMap to verify behavior with null mapper.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntFunction;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNullMapper();
        testFlatMapWithValidMapper();
    }

    private static void testFlatMapWithNullMapper() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            stream.flatMap(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testFlatMapWithValidMapper() {
        IntStream stream = IntStream.of(1, 2);
        IntFunction<? extends IntStream> mapper = i -> IntStream.of(i, i * 10);
        IntStream result = stream.flatMap(mapper);
        int[] expected = {1, 10, 2, 20};
        int index = 0;
        for (int val : result.toArray()) {
            if (val != expected[index++]) {
                throw new AssertionError("Unexpected value: " + val);
            }
        }
    }
}
