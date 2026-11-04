/*
 * @test
 * @summary Test ReferencePipeline.findAny() method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        // Test findAny on a non-empty stream
        Stream<String> stream1 = Stream.of("a", "b", "c");
        Optional<String> result1 = stream1.findAny();
        if (!result1.isPresent()) {
            throw new AssertionError("Expected a value from findAny() on non-empty stream");
        }

        // Test findAny on an empty stream
        Stream<String> stream2 = Stream.<String>empty();
        Optional<String> result2 = stream2.findAny();
        if (result2.isPresent()) {
            throw new AssertionError("Expected empty Optional from findAny() on empty stream");
        }

        // Test findAny on a single-element stream
        Stream<Integer> stream3 = Stream.of(42);
        Optional<Integer> result3 = stream3.findAny();
        if (!result3.isPresent() || result3.get() != 42) {
            throw new AssertionError("Expected Optional containing 42 from findAny() on single-element stream");
        }

        System.out.println("All tests passed.");
    }
}
