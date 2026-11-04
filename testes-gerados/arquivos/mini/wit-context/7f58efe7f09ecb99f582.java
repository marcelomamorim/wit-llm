/*
 * @test
 * @summary Test ReferencePipeline.peek method for null argument and normal behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testPeekWithNullAction();
        testPeekWithValidAction();
    }

    static void testPeekWithNullAction() {
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(
            () -> null, 0, false) {
            @Override
            public Stream<String> peek(Consumer<? super String> action) {
                return super.peek(action);
            }
        };

        try {
            pipeline.peek(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testPeekWithValidAction() {
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(
            () -> null, 0, false) {
            @Override
            public Stream<String> peek(Consumer<? super String> action) {
                return super.peek(action);
            }
        };

        final boolean[] called = {false};
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        };

        Stream<String> result = pipeline.peek(action);
        if (result == null) {
            throw new AssertionError("peek should not return null");
        }

        // The returned Stream is a StatelessOp subclass, cannot directly test accept without full pipeline
        // But we can test that the returned Stream is not null and action is not called yet
        if (called[0]) {
            throw new AssertionError("Action should not be called during peek creation");
        }
    }
}