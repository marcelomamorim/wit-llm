/*
 * @test
 * @summary Test ReferencePipeline.findFirst() method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testFindFirstOnNonEmptyStream();
        testFindFirstOnEmptyStream();
    }

    private static void testFindFirstOnNonEmptyStream() {
        Stream<String> stream = Stream.of("a", "b", "c");
        Optional<String> result = stream.findFirst();
        if (!result.isPresent() || !"a".equals(result.get())) {
            throw new AssertionError("Expected Optional containing 'a' but got: " + result);
        }
    }

    private static void testFindFirstOnEmptyStream() {
        Stream<String> stream = Stream.empty();
        Optional<String> result = stream.findFirst();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional but got: " + result);
        }
    }
}
