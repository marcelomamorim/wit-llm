/*
 * @test
 * @summary Test ReferencePipeline.max(Comparator) method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testMaxWithNonEmptyStream();
        testMaxWithEmptyStream();
        testMaxWithNullComparator();
    }

    private static void testMaxWithNonEmptyStream() {
        Stream<String> stream = Stream.of("a", "bb", "ccc");
        Optional<String> max = stream.max(Comparator.comparingInt(String::length));
        if (!max.isPresent() || !"ccc".equals(max.get())) {
            throw new AssertionError("Expected max element to be 'ccc', got: " + max);
        }
    }

    private static void testMaxWithEmptyStream() {
        Stream<String> stream = Stream.<String>empty();
        Optional<String> max = stream.max(Comparator.naturalOrder());
        if (max.isPresent()) {
            throw new AssertionError("Expected empty Optional for max on empty stream, got: " + max);
        }
    }

    private static void testMaxWithNullComparator() {
        Stream<String> stream = Stream.of("a", "b");
        try {
            stream.max(null);
            throw new AssertionError("Expected NullPointerException when comparator is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
