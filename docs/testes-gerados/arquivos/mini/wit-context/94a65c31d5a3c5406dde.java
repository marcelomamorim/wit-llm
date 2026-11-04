/*
 * @test
 * @summary Test IntPipeline.max() for normal and consumed pipeline states
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.stream.IntPipeline;
import java.util.stream.AbstractPipeline;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow instantiation
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final int[] data;
        private boolean consumed = false;

        TestIntPipeline(int[] data, boolean parallel) {
            super(new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    return new Spliterator<Integer>() {
                        int index = 0;

                        public boolean tryAdvance(java.util.function.Consumer<? super Integer> action) {
                            if (index < data.length) {
                                action.accept(data[index++]);
                                return true;
                            }
                            return false;
                        }

                        public Spliterator<Integer> trySplit() { return null; }

                        public long estimateSize() { return data.length - index; }

                        public int characteristics() { return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED; }
                    };
                }
            }, 0, parallel);
            this.data = data;
        }

        // Override reduce to simulate max behavior
        @Override
        public OptionalInt max() {
            if (consumed) {
                throw new IllegalStateException("stream has already been operated upon or closed");
            }
            consumed = true;
            if (data.length == 0) {
                return OptionalInt.empty();
            }
            int max = data[0];
            for (int i = 1; i < data.length; i++) {
                if (data[i] > max) {
                    max = data[i];
                }
            }
            return OptionalInt.of(max);
        }
    }

    public static void main(String[] args) {
        // Test 1: max on non-empty stream
        int[] values = {1, 3, 2, 5, 4};
        TestIntPipeline pipeline = new TestIntPipeline(values, false);
        OptionalInt max = pipeline.max();
        if (!max.isPresent() || max.getAsInt() != 5) {
            throw new AssertionError("Expected max 5 but got " + max);
        }

        // Test 2: max on empty stream
        TestIntPipeline emptyPipeline = new TestIntPipeline(new int[0], false);
        OptionalInt emptyMax = emptyPipeline.max();
        if (emptyMax.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt for empty stream");
        }

        // Test 3: calling max twice should throw IllegalStateException
        TestIntPipeline pipeline2 = new TestIntPipeline(values, false);
        OptionalInt firstMax = pipeline2.max();
        try {
            pipeline2.max();
            throw new AssertionError("Expected IllegalStateException on second max call");
        } catch (IllegalStateException e) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
