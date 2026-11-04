/*
 * @test
 * @summary Test IntPipeline.peek method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    // Minimal IntPipeline subclass to test peek method
    static class TestIntPipeline extends IntPipeline<Object> {
        TestIntPipeline() {
            super((Spliterator<Integer>) null, 0, false);
        }
    }

    public static void main(String[] args) {
        testPeekWithNullAction();
        testPeekWithActionInvoked();
    }

    private static void testPeekWithNullAction() {
        IntPipeline<Object> pipeline = new TestIntPipeline();
        try {
            pipeline.peek(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPeekWithActionInvoked() {
        IntPipeline<Object> pipeline = new TestIntPipeline();

        final int[] seen = new int[1];
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                seen[0] = value;
            }
        };

        IntStream peeked = pipeline.peek(action);

        // The returned IntStream is a StatelessOp anonymous subclass
        // We cannot directly invoke accept on it, but we can test that
        // the returned object is not null and is an IntStream
        if (peeked == null) {
            throw new RuntimeException("peek returned null");
        }

        // Since we cannot access internals, test that action is not invoked prematurely
        if (seen[0] != 0) {
            throw new RuntimeException("Action invoked prematurely");
        }
    }
}
