/*
 * @test
 * @summary Test ReferencePipeline.findAny() for normal and illegal state scenarios
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Spliterators;
import java.util.function.Supplier;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyOnEmptyStream();
        testFindAnyOnNonEmptyStream();
        testFindAnyOnConsumedStream();
    }

    // Test findAny() on an empty stream returns Optional.empty
    private static void testFindAnyOnEmptyStream() {
        Stream<String> stream = Stream.empty();
        Optional<String> result = stream.findAny();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional for empty stream");
        }
    }

    // Test findAny() on a non-empty stream returns a present Optional
    private static void testFindAnyOnNonEmptyStream() {
        Stream<String> stream = Stream.of("a", "b", "c");
        Optional<String> result = stream.findAny();
        if (!result.isPresent()) {
            throw new AssertionError("Expected non-empty Optional for non-empty stream");
        }
        String val = result.get();
        if (!("a".equals(val) || "b".equals(val) || "c".equals(val))) {
            throw new AssertionError("Unexpected value returned by findAny: " + val);
        }
    }

    // Test that calling findAny() twice on the same stream throws IllegalStateException
    private static void testFindAnyOnConsumedStream() {
        Stream<String> stream = Stream.of("x", "y");
        Optional<String> firstCall = stream.findAny();
        try {
            stream.findAny();
            throw new AssertionError("Expected IllegalStateException on second findAny call on consumed stream");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
