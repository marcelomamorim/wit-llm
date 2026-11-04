/*
 * @test
 * @summary Tests for IntPipeline methods, focusing on mapToObj behavior.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.Stream;
import java.util.function.IntFunction;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMapToObjWithNullMapper();
        testMapToObjWithValidMapper();
    }

    private static void testMapToObjWithNullMapper() {
        IntPipeline pipeline = createSamplePipeline();
        try {
            pipeline.mapToObj(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testMapToObjWithValidMapper() {
        IntPipeline pipeline = createSamplePipeline();
        IntFunction<String> mapper = (intValue) -> "Number: " + intValue;
        Stream<String> resultStream = pipeline.mapToObj(mapper);
        String[] results = resultStream.toArray(String[]::new);
        if (results.length != 3 || !results[0].equals("Number: 1") || !results[1].equals("Number: 2") || !results[2].equals("Number: 3")) {
            System.err.println("mapToObj with valid mapper failed: " + java.util.Arrays.toString(results));
            System.exit(1);
        }
    }

    private static IntPipeline createSamplePipeline() {
        return (IntPipeline) java.util.stream.IntStream.of(1, 2, 3);
    }
}
