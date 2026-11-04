/*
 * @test
 * @summary Test for IntPipeline.forEachOrdered method behavior.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedWithSequential();
        testForEachOrderedWithParallel();
    }

    private static void testForEachOrderedWithSequential() {
        int[] array = {1, 2, 3};
        StringBuilder sb = new StringBuilder();
        IntConsumer consumer = sb::append;
        IntPipeline stream = IntPipeline.of(array, 0, array.length);
        try {
            stream.forEachOrdered(consumer);
            if (!sb.toString().equals("123")) {
                throw new AssertionError("Sequential forEachOrdered did not process elements in order: " + sb.toString());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in sequential forEachOrdered", e);
        }
    }

    private static void testForEachOrderedWithParallel() {
        int[] array = {4, 5, 6};
        StringBuilder sb = new StringBuilder();
        IntConsumer consumer = sb::append;
        IntPipeline stream = IntPipeline.of(array, 0, array.length).parallel();
        try {
            stream.forEachOrdered(consumer);
            String result = sb.toString();
            if (!(result.contains("4") && result.contains("5") && result.contains("6"))) {
                throw new AssertionError("Parallel forEachOrdered did not process all elements: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in parallel forEachOrdered", e);
        }
    }
}
