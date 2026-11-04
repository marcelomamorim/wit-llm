/*
 * @test
 * @summary Test LongPipeline.findFirst() for normal and illegal state scenarios
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.stream.LongStream;
import java.util.stream.LongPipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamSupport;
import java.util.function.Supplier;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to allow testing
    static class TestLongPipeline extends LongPipeline<Long> {

        TestLongPipeline(Supplier<? extends Spliterator<Long>> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestLongPipeline(Spliterator<Long> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestLongPipeline(AbstractPipeline<?, Long, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        // Expose evaluate method for testing
        public <R> R testEvaluate(java.util.stream.TerminalOp<Long, R> terminalOp) {
            return evaluate(terminalOp);
        }
    }

    public static void main(String[] args) {
        testFindFirstNormal();
        testFindFirstIllegalState();
    }

    private static void testFindFirstNormal() {
        // Create a LongStream with known elements
        LongStream stream = LongStream.of(10L, 20L, 30L);

        // Wrap the stream's spliterator in a TestLongPipeline
        TestLongPipeline pipeline = new TestLongPipeline(stream.spliterator(), 0, false);

        OptionalLong result = pipeline.findFirst();

        if (!result.isPresent() || result.getAsLong() != 10L) {
            throw new AssertionError("Expected first element 10L, got " + result);
        }
    }

    private static void testFindFirstIllegalState() {
        // The findFirst method throws IllegalStateException if the pipeline is linked or consumed
        // We simulate this by calling findFirst twice on the same pipeline

        LongStream stream = LongStream.of(1L, 2L, 3L);
        TestLongPipeline pipeline = new TestLongPipeline(stream.spliterator(), 0, false);

        // First call should succeed
        OptionalLong first = pipeline.findFirst();
        if (!first.isPresent() || first.getAsLong() != 1L) {
            throw new AssertionError("Expected first element 1L, got " + first);
        }

        // Second call should throw IllegalStateException because pipeline is consumed
        boolean threw = false;
        try {
            pipeline.findFirst();
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second findFirst call");
        }
    }
}
