/*
 * @test
 * @summary Test IntPipeline.peek(IntConsumer) for null argument and normal behavior
 * @run main IntPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    static class DummyIntPipeline extends java.util.stream.IntPipeline<Integer> {
        DummyIntPipeline() {
            super((Spliterator<Integer>) null, 0, false);
        }

        @Override
        public java.util.stream.IntStream peek(IntConsumer action) {
            return super.peek(action);
        }
    }

    public static void main(String[] args) {
        testPeekNullAction();
        testPeekNormalAction();
    }

    private static void testPeekNullAction() {
        DummyIntPipeline pipeline = new DummyIntPipeline();
        try {
            pipeline.peek(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPeekNormalAction() {
        DummyIntPipeline pipeline = new DummyIntPipeline();
        final int[] seen = new int[1];
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                seen[0] = value;
            }
        };

        IntStream result = pipeline.peek(action);
        if (result == null) {
            throw new AssertionError("peek should not return null");
        }

        // Since DummyIntPipeline has no source, we cannot run the stream, but we can test that
        // the returned stream is not null and is an IntStream
        if (!(result instanceof IntStream)) {
            throw new AssertionError("peek should return an IntStream");
        }
    }
}
