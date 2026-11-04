/*
 * @test
 * @summary Test for IntPipeline.forEachOrdered method to ensure it applies the action in order.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testForEachOrderedExecutesInOrder();
    }

    public static void testForEachOrderedExecutesInOrder() {
        int[] result = {0};
        IntStream stream = IntStream.of(1, 2, 3);
        stream = stream.parallel();
        stream.forEachOrdered(i -> {
            result[0] = result[0] * 10 + i;
        });
        // Since forEachOrdered should process in encounter order, expect 123
        if (result[0] != 123) {
            throw new AssertionError("Expected 123 but got " + result[0]);
        }
    }
}