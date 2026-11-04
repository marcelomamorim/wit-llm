/*
 * @test    @summary Test the findAny() method of ReferencePipeline for various stream states
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyOnEmptyStream();
        testFindAnyOnSingleElementStream();
        testFindAnyOnMultipleElementsStream();
    }

    static void testFindAnyOnEmptyStream() {
        java.util.stream.Stream<Integer> emptyStream = java.util.stream.Stream.empty();
        Optional<Integer> result = emptyStream.filter(x -> x > 0).findAny();
        assert !result.isPresent() : "Expected empty Optional for empty stream";
    }

    static void testFindAnyOnSingleElementStream() {
        java.util.stream.Stream<String> singleStream = java.util.stream.Stream.of("test");
        Optional<String> result = singleStream.findAny();
        assert result.isPresent() : "Expected present Optional for single element stream";
        assert result.get().equals("test") : "Expected value 'test'";
    }

    static void testFindAnyOnMultipleElementsStream() {
        java.util.stream.Stream<Integer> stream = java.util.stream.Stream.of(1, 2, 3);
        Optional<Integer> result = stream.findAny();
        assert result.isPresent() : "Expected present Optional for multiple elements";
        Integer val = result.get();
        assert val >= 1 && val <= 3 : "Value should be within the stream elements";
    }
}
