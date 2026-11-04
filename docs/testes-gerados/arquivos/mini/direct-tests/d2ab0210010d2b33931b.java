/*
 * @test
 * @summary Test IntPipeline.skip(long) method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.AbstractPipeline;
import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow testing skip
    static class TestIntPipeline extends IntPipeline<Integer> {
        TestIntPipeline(IntStream upstream) {
            super(upstream.spliterator(), 0, false);
        }

        @Override
        public void forEach(IntConsumer action) {
            IntStream stream = StreamSupport.intStream(spliterator(), false);
            stream.forEach(action);
        }

        @Override
        public Spliterator<Integer> spliterator() {
            return Spliterators.spliteratorUnknownSize(
                StreamSupport.intStream(super.spliterator(), false).iterator(), 0);
        }

        @Override
        public IntStream skip(long n) {
            return super.skip(n);
        }
    }

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    private static void testSkipNegative() {
        TestIntPipeline pipeline = new TestIntPipeline(IntStream.of(1, 2, 3));
        try {
            pipeline.skip(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals("-1")) {
                throw new RuntimeException("Exception message mismatch", e);
            }
        }
    }

    private static void testSkipZero() {
        IntStream base = IntStream.of(1, 2, 3);
        TestIntPipeline pipeline = new TestIntPipeline(base);
        IntStream result = pipeline.skip(0);
        if (result != pipeline) {
            throw new RuntimeException("skip(0) should return the same instance");
        }
    }

    private static void testSkipPositive() {
        IntStream base = IntStream.of(1, 2, 3, 4, 5);
        TestIntPipeline pipeline = new TestIntPipeline(base);
        IntStream skipped = pipeline.skip(2);
        int[] expected = {3, 4, 5};
        int[] actual = skipped.toArray();
        if (actual.length != expected.length) {
            throw new RuntimeException("skip(2) result length mismatch");
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new RuntimeException("skip(2) result element mismatch at index " + i);
            }
        }
    }
}
