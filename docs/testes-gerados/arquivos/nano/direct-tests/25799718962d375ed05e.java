/*
 * @test    @summary Test the findFirst() method of ReferencePipeline for correct Optional return.
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFindFirstWithElements();
        testFindFirstEmpty();
    }

    static void testFindFirstWithElements() {
        // Create a stream with elements
        Optional<String> result = ReferencePipeline.of(java.util.Arrays.asList("a", "b", "c")::iterator)
            .findFirst();
        assert result.isPresent() : "Expected present Optional";
        assert "a".equals(result.get()) : "Expected 'a', got " + result.get();
    }

    static void testFindFirstEmpty() {
        // Create an empty stream
        Optional<String> result = ReferencePipeline.of(java.util.Collections.emptyList()::iterator)
            .findFirst();
        assert !result.isPresent() : "Expected empty Optional";
    }
}
