/*
 * @test
 * @summary Test for LongPipeline's mapToObj method.
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongPipeline;
import java.util.stream.Stream;
import java.util.function.LongFunction;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testMapToObjWithNullMapper();
        testMapToObjWithValidMapper();
    }

    static void testMapToObjWithNullMapper() {
        try {
            LongPipeline pipeline = createSamplePipeline();
            pipeline.mapToObj(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testMapToObjWithValidMapper() {
        LongPipeline pipeline = createSamplePipeline();
        Stream<String> resultStream = pipeline.mapToObj(Long::toString);
        String[] expected = {"1", "2", "3"};
        String[] actual = resultStream.toArray(String[]::new);
        if (!java.util.Arrays.equals(expected, actual)) {
            System.err.println("mapToObj did not produce expected output");
            System.exit(1);
        }
    }

    static LongPipeline createSamplePipeline() {
        return (LongPipeline) java.util.stream.LongStream.of(1L, 2L, 3L).map(x -> x);
    }
}
