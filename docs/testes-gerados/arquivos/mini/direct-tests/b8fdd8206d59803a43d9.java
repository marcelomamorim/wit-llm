/*
 * @test
 * @summary Test IntPipeline.map(IntUnaryOperator) for basic functionality and null check
 * @run main IntPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntUnaryOperator;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow testing map()
    static class TestIntPipeline extends java.util.stream.IntPipeline<Integer> {
        TestIntPipeline(Spliterator<Integer> source) {
            super(source, 0, false);
        }

        @Override
        public java.util.stream.IntStream map(IntUnaryOperator mapper) {
            return super.map(mapper);
        }

        // Implement abstract methods from AbstractPipeline
        @Override
        public void forEach(java.util.function.IntConsumer action) {
            Objects.requireNonNull(action);
            Spliterator<Integer> spliterator = sourceSpliterator();
            if (spliterator == null) {
                throw new IllegalStateException("sourceSpliterator is null");
            }
            spliterator.forEachRemaining(i -> action.accept(i));
        }

        @Override
        public Spliterator<Integer> sourceSpliterator() {
            return super.sourceSpliterator();
        }
    }

    public static void main(String[] args) {
        // Test 1: map with a simple doubling function
        Integer[] input = {1, 2, 3};
        Spliterator<Integer> spliterator = Spliterators.spliterator(input, 0);
        TestIntPipeline pipeline = new TestIntPipeline(spliterator);

        IntUnaryOperator doubler = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * 2;
            }
        };

        IntStream mappedStream = pipeline.map(doubler);

        final int[] results = new int[3];
        final int[] index = {0};
        mappedStream.forEach((int val) -> {
            results[index[0]++] = val;
        });

        if (results.length != 3 || results[0] != 2 || results[1] != 4 || results[2] != 6) {
            throw new AssertionError("map did not correctly apply the function");
        }

        // Test 2: map with identity function
        IntUnaryOperator identity = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand;
            }
        };

        IntStream identityStream = pipeline.map(identity);
        final int[] identityResults = new int[3];
        final int[] idx = {0};
        identityStream.forEach((int val) -> {
            identityResults[idx[0]++] = val;
        });

        if (identityResults.length != 3 || identityResults[0] != 1 || identityResults[1] != 2 || identityResults[2] != 3) {
            throw new AssertionError("map did not correctly apply the identity function");
        }

        // Test 3: map with null argument should throw NullPointerException
        boolean npeThrown = false;
        try {
            pipeline.map(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("map did not throw NullPointerException when passed null");
        }
    }
}
