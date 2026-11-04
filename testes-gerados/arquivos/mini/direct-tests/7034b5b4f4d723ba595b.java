/*
 * @test
 * @summary Test IntPipeline.sum() method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testSumEmptyStream();
        testSumSingleElement();
        testSumMultipleElements();
    }

    private static void testSumEmptyStream() {
        IntStream empty = IntStream.empty();
        int result = empty.sum();
        if (result != 0) {
            throw new AssertionError("Sum of empty stream should be 0 but was " + result);
        }
    }

    private static void testSumSingleElement() {
        IntStream single = IntStream.of(42);
        int result = single.sum();
        if (result != 42) {
            throw new AssertionError("Sum of single-element stream should be 42 but was " + result);
        }
    }

    private static void testSumMultipleElements() {
        IntStream multiple = IntStream.of(1, 2, 3, 4, 5);
        int result = multiple.sum();
        if (result != 15) {
            throw new AssertionError("Sum of 1..5 should be 15 but was " + result);
        }
    }
}
