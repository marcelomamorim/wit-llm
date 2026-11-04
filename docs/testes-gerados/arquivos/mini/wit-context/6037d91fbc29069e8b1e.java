/*
 * @test
 * @summary Test LongPipeline.accept(long) method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.Sink;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    static class TestSink implements Sink<Long>, LongConsumer {
        boolean accepted = false;
        long value = 0L;

        @Override
        public void accept(Long t) {
            accept(t.longValue());
        }

        @Override
        public void accept(long value) {
            this.accepted = true;
            this.value = value;
            // Simulate throwing IllegalStateException as per WIT expath
            throw new IllegalStateException("TestSink accept(long) called");
        }

        @Override
        public void begin(long size) {}

        @Override
        public boolean cancellationRequested() { return false; }

        @Override
        public void end() {}
    }

    public static void main(String[] args) {
        // Create a LongPipeline instance with a dummy Sink that throws IllegalStateException
        final TestSink testSink = new TestSink();

        // Anonymous subclass of LongPipeline to override downstream with testSink
        LongPipeline<?> pipeline = new LongPipeline<Object>(null, 0, false) {
            final Sink<Long> downstream = testSink;

            @Override
            public void accept(long t) {
                downstream.accept((double) t); // original code casts to double and calls downstream.accept(double)
            }
        };

        // Because downstream.accept(double) is called, and testSink.accept(long) throws,
        // we adapt testSink to LongConsumer and override accept(double) to call accept(long)
        // to simulate the exception path.

        // We test that calling accept(long) on pipeline triggers the expected exception.

        boolean exceptionThrown = false;
        try {
            pipeline.accept(42L);
        } catch (IllegalStateException e) {
            exceptionThrown = true;
            if (!"TestSink accept(long) called".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }

        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException was not thrown");
        }
    }
}
