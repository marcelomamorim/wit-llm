/*
 * @test
 * @summary Test ReferencePipeline.map method for null mapper and normal mapping
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapWithNullMapper();
        testMapWithIdentityFunction();
    }

    private static void testMapWithNullMapper() {
        // Create a simple ReferencePipeline instance via Stream.of().map() to get a ReferencePipeline
        Stream<String> baseStream = Stream.of("a", "b", "c");
        ReferencePipeline<String, String> refPipeline = (ReferencePipeline<String, String>) baseStream;

        boolean npeThrown = false;
        try {
            refPipeline.map(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when mapper is null");
        }
    }

    private static void testMapWithIdentityFunction() {
        Stream<String> baseStream = Stream.of("x", "y", "z");
        ReferencePipeline<String, String> refPipeline = (ReferencePipeline<String, String>) baseStream;

        Stream<String> mappedStream = refPipeline.map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        });

        Object[] results = mappedStream.toArray();
        if (results.length != 3 || !"x".equals(results[0]) || !"y".equals(results[1]) || !"z".equals(results[2])) {
            throw new AssertionError("Mapped stream did not produce expected elements");
        }
    }
}
