/*
 * @test
 * @summary Test ReferencePipeline.accept(P_OUT) method for expected behavior and exception paths
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.AbstractPipeline;
import java.util.function.Supplier;
import java.util.Spliterators;

public class ReferencePipelineWitupTest {

    static class TestPipeline extends ReferencePipeline<Integer, Integer> {
        private final Consumer<Integer> downstream;
        private final IntFunction<Integer> mapper;

        TestPipeline(Consumer<Integer> downstream, IntFunction<Integer> mapper) {
            super(new Supplier<Spliterator<?>>() {
                public Spliterator<?> get() {
                    return Spliterators.emptySpliterator();
                }
            }, 0, false);
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override
        public void accept(Integer u) {
            downstream.accept(mapper.apply(u));
        }
    }

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptThrows();
    }

    private static void testAcceptNormal() {
        final int[] received = new int[1];
        Consumer<Integer> downstream = new Consumer<Integer>() {
            public void accept(Integer i) {
                received[0] = i;
            }
        };
        IntFunction<Integer> mapper = new IntFunction<Integer>() {
            public Integer apply(int value) {
                return value * 2;
            }
        };

        TestPipeline pipeline = new TestPipeline(downstream, mapper);
        pipeline.accept(5);
        if (received[0] != 10) {
            throw new AssertionError("Expected 10 but got " + received[0]);
        }
    }

    private static void testAcceptThrows() {
        Consumer<Integer> downstream = new Consumer<Integer>() {
            public void accept(Integer i) {
                throw new IllegalStateException("forced");
            }
        };
        IntFunction<Integer> mapper = new IntFunction<Integer>() {
            public Integer apply(int value) {
                return value;
            }
        };

        TestPipeline pipeline = new TestPipeline(downstream, mapper);

        boolean thrown = false;
        try {
            pipeline.accept(1);
        } catch (IllegalStateException e) {
            thrown = true;
            if (!"forced".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException was not thrown");
        }
    }
}
