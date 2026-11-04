/*
 * @test
 * @summary Test for IntPipeline.forEachOrdered method to ensure it applies the action to each element in order.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedSequential();
        testForEachOrderedParallel();
    }

    static void testForEachOrderedSequential() {
        int[] array = {1, 2, 3, 4, 5};
        StringBuilder sb = new StringBuilder();
        IntConsumer consumer = (int value) -> sb.append(value).append(",");
        IntPipeline stream = IntPipeline.of(array, 0, array.length);
        stream.forEachOrdered(consumer);
        String result = sb.toString();
        if (!result.equals("1,2,3,4,5,")) {
            throw new AssertionError("Sequential forEachOrdered did not process elements in order: " + result);
        }
    }

    static void testForEachOrderedParallel() {
        int[] array = {1, 2, 3, 4, 5};
        StringBuilder sb = new StringBuilder();
        Object lock = new Object();
        IntConsumer consumer = (int value) -> {
            synchronized (lock) {
                sb.append(value).append(",");
            }
        };
        IntPipeline stream = IntPipeline.of(array, 0, array.length).parallel();
        stream.forEachOrdered(consumer);
        String result = sb.toString();
        String[] parts = result.split(",");
        for (int i = 0; i < array.length; i++) {
            if (!parts[i].equals(String.valueOf(array[i]))) {
                throw new AssertionError("Parallel forEachOrdered did not process elements in order: " + result);
            }
        }
    }
}
