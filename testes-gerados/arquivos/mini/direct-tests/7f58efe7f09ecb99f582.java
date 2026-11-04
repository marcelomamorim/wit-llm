/*
 * @test
 * @summary Test ReferencePipeline.peek method behavior and null argument
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;
import java.util.function.Consumer;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testPeekWithAction();
        testPeekWithNullAction();
    }

    static void testPeekWithAction() {
        // Create a stream with known elements
        Stream<String> stream = Stream.of("a", "b", "c");

        // List to collect peeked elements
        final List<String> peeked = new ArrayList<>();

        // Use peek to collect elements
        Stream<String> peekedStream = stream.peek(new Consumer<String>() {
            public void accept(String s) {
                peeked.add(s);
            }
        });

        // Consume the stream to trigger peek
        List<String> result = peekedStream.toList();

        // Check that peeked elements match the stream elements
        if (!peeked.equals(result)) {
            throw new AssertionError("Peeked elements do not match stream elements: " + peeked + " vs " + result);
        }

        // Check that result contains expected elements
        if (result.size() != 3 || !result.get(0).equals("a") || !result.get(1).equals("b") || !result.get(2).equals("c")) {
            throw new AssertionError("Stream elements unexpected: " + result);
        }
    }

    static void testPeekWithNullAction() {
        try {
            Stream<String> stream = Stream.of("x");
            stream.peek(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
