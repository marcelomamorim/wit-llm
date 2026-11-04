/*
 * @test
 * @summary Tests for java.util.stream.ReferencePipeline methods
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Consumer;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testPeekWithNullAction();
    }

    public static void testPeekWithNullAction() {
        Stream<Object> stream = Stream.of(1, 2, 3);
        try {
            stream.peek(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
