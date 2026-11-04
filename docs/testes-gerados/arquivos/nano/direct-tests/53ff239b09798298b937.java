/*
 * @test
 * @summary Test for IntPipeline.forEach method to ensure it applies the action to each element.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testForEachSequential();
        testForEachParallel();
    }

    static void testForEachSequential() {
        final StringBuilder result = new StringBuilder();
        IntConsumer consumer = result::append;
        IntPipeline.of(new int[]{1, 2, 3})
                   .forEach(consumer);
        assert result.toString().equals("123") : "Sequential forEach did not process all elements";
    }

    static void testForEachParallel() {
        final StringBuilder result = new StringBuilder();
        IntConsumer consumer = result::append;
        IntPipeline.of(new int[]{4, 5, 6})
                   .parallel()
                   .forEach(consumer);
        String output = result.toString();
        assert output.contains("4") && output.contains("5") && output.contains("6") : "Parallel forEach did not process all elements";
    }
}
