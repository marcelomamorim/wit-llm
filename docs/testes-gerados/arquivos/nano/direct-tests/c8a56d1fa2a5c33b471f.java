/*
 * @test
 * @summary Tests the reduce method of ReferencePipeline for correctness and regressions
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testReduceWithNonEmptyStream();
        testReduceWithEmptyStream();
        testReduceWithSingleElement();
    }

    static void testReduceWithNonEmptyStream() {
        Integer[] data = {1, 2, 3, 4};
        Integer result = ReferencePipeline.of(data)
            .reduce(0, (a, b) -> a + b, Integer::sum);
        if (result != 10) {
            throw new AssertionError("Expected 10, but got " + result);
        }
    }

    static void testReduceWithEmptyStream() {
        Integer[] data = {};
        Integer result = ReferencePipeline.of(data)
            .reduce(0, (a, b) -> a + b, Integer::sum);
        if (result != 0) {
            throw new AssertionError("Expected 0, but got " + result);
        }
    }

    static void testReduceWithSingleElement() {
        Integer[] data = {42};
        Integer result = ReferencePipeline.of(data)
            .reduce(0, (a, b) -> a + b, Integer::sum);
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }
}
