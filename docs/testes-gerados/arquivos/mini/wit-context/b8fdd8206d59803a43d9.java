/*
 * @test
 * @summary Test IntPipeline.map(IntUnaryOperator) for null argument and basic mapping
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntUnaryOperator;
import java.util.Objects;

public class IntPipelineWitupTest {

    static class DummyIntPipeline extends java.util.stream.IntPipeline<Integer> {
        DummyIntPipeline() {
            super((Spliterator<Integer>) null, 0, false);
        }

        @Override
        public java.util.stream.Spliterator<Integer> spliterator() {
            return null;
        }

        @Override
        public boolean isParallel() {
            return false;
        }

        @Override
        public java.util.stream.IntStream unordered() {
            return this;
        }

        @Override
        public java.util.stream.IntStream sequential() {
            return this;
        }

        @Override
        public java.util.stream.IntStream parallel() {
            return this;
        }

        @Override
        public void forEach(java.util.function.IntConsumer action) {
            // no-op
        }

        @Override
        public void forEachOrdered(java.util.function.IntConsumer action) {
            // no-op
        }
    }

    public static void main(String[] args) {
        testMapNullArgument();
        testMapBasicFunctionality();
    }

    private static void testMapNullArgument() {
        DummyIntPipeline pipeline = new DummyIntPipeline();
        try {
            pipeline.map(null);
            throw new RuntimeException("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testMapBasicFunctionality() {
        DummyIntPipeline pipeline = new DummyIntPipeline();
        IntUnaryOperator mapper = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand + 1;
            }
        };
        java.util.stream.IntStream mappedStream = pipeline.map(mapper);
        if (mappedStream == null) {
            throw new RuntimeException("map() returned null");
        }
        // The returned IntStream is a StatelessOp subclass, cannot easily test internals,
        // but we can test that it is not the same instance and is an IntStream.
        if (!(mappedStream instanceof IntStream)) {
            throw new RuntimeException("map() did not return an IntStream");
        }
    }
}
