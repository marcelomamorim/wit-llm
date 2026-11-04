/*
 * @test
 * @summary Test IntPipeline.forEach(IntConsumer) for normal and illegal state
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.AbstractPipeline;
import java.util.stream.ForEachOps;

public class IntPipelineWitupTest {

    // Minimal IntPipeline subclass to test forEach
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final IntStream stream;

        TestIntPipeline(IntStream stream) {
            super(new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    return stream.spliterator();
                }
            }, 0, false);
            this.stream = stream;
        }

        @Override
        public void forEach(IntConsumer action) {
            // call the actual method under test
            super.forEach(action);
        }
    }

    public static void main(String[] args) {
        testForEachNormal();
        testForEachNullAction();
    }

    private static void testForEachNormal() {
        // Create a simple IntStream and wrap it
        IntStream baseStream = IntStream.of(1, 2, 3);
        TestIntPipeline pipeline = new TestIntPipeline(baseStream);

        final int[] sum = new int[1];
        pipeline.forEach(new IntConsumer() {
            public void accept(int value) {
                sum[0] += value;
            }
        });

        if (sum[0] != 6) {
            throw new AssertionError("Expected sum 6 but got " + sum[0]);
        }
    }

    private static void testForEachNullAction() {
        // According to IntStream contract, passing null to forEach throws NullPointerException
        IntStream baseStream = IntStream.of(1);
        TestIntPipeline pipeline = new TestIntPipeline(baseStream);

        try {
            pipeline.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
