/*
 * @test
 * @summary Test IntPipeline.findAny() for normal and consumed pipeline states
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.IntPipeline;
import java.util.stream.AbstractPipeline;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    // Minimal IntPipeline subclass to test findAny()
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final IntStream stream;

        TestIntPipeline(IntStream stream) {
            super(new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    // Wrap IntStream's spliterator as Spliterator<Integer>
                    return (Spliterator<Integer>)(Spliterator<?>) stream.spliterator();
                }
            }, 0, false);
            this.stream = stream;
        }

        @Override
        public OptionalInt findAny() {
            return super.findAny();
        }

        @Override
        public <P_IN> AbstractPipeline<P_IN, Integer, IntStream> opWrapSink(int flags, AbstractPipeline<P_IN, Integer, IntStream> upstream) {
            // Not used in this test
            return null;
        }
    }

    public static void main(String[] args) {
        // Test 1: findAny() on a non-empty stream returns a present OptionalInt
        IntStream stream1 = IntStream.of(10, 20, 30);
        TestIntPipeline pipeline1 = new TestIntPipeline(stream1);
        OptionalInt result1 = pipeline1.findAny();
        if (!result1.isPresent()) {
            throw new AssertionError("Expected present OptionalInt from findAny() on non-empty stream");
        }
        int val1 = result1.getAsInt();
        if (val1 != 10 && val1 != 20 && val1 != 30) {
            throw new AssertionError("findAny() returned unexpected value: " + val1);
        }

        // Test 2: findAny() on an empty stream returns empty OptionalInt
        IntStream stream2 = IntStream.empty();
        TestIntPipeline pipeline2 = new TestIntPipeline(stream2);
        OptionalInt result2 = pipeline2.findAny();
        if (result2.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt from findAny() on empty stream");
        }

        // Test 3: findAny() on a consumed stream should throw IllegalStateException
        // We simulate consumption by calling findAny() twice on the same pipeline
        IntStream stream3 = IntStream.of(1, 2, 3);
        TestIntPipeline pipeline3 = new TestIntPipeline(stream3);
        OptionalInt firstCall = pipeline3.findAny();
        try {
            pipeline3.findAny();
            throw new AssertionError("Expected IllegalStateException on second findAny() call on consumed pipeline");
        } catch (IllegalStateException e) {
            // Expected exception
        }

        System.out.println("All tests passed.");
    }
}
