/*
 * @test
 * @summary Test ReferencePipeline.map method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;
import java.util.function.Function;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapWithValidFunction();
        testMapWithNullFunction();
    }

    static void testMapWithValidFunction() {
        Stream<String> baseStream = Stream.of("a", "bb", "ccc");
        Function<String, Integer> lengthMapper = new Function<String, Integer>() {
            public Integer apply(String s) {
                return s.length();
            }
        };

        Stream<Integer> mappedStream = baseStream.map(lengthMapper);

        // Collect results to array and verify
        Object[] results = mappedStream.toArray();

        if (results.length != 3) {
            throw new AssertionError("Expected 3 elements but got " + results.length);
        }
        if (!results[0].equals(1) || !results[1].equals(2) || !results[2].equals(3)) {
            throw new AssertionError("Mapped results do not match expected lengths");
        }
    }

    static void testMapWithNullFunction() {
        Stream<String> baseStream = Stream.of("x");
        try {
            baseStream.map(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
