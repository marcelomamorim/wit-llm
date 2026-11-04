/*
 * @test
 * @summary Test for LongPipeline.accept(long) method to observe behavior and exception handling.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.Sink;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testAcceptWithValidInput();
        testAcceptWithNullSink();
    }

    private static void testAcceptWithValidInput() {
        try {
            // Create a dummy sink that records the accepted value
            class DummySink implements Sink<Long> {
                long received = 0;
                @Override
                public void accept(Long t) {
                    received = t;
                }
                // Other methods are omitted for brevity
            }
            DummySink sink = new DummySink();
            // Create a LongPipeline with a dummy spliterator (not used in this test)
            LongPipeline<Long> pipeline = new LongPipeline<>(() -> null, 0, false) {
                @Override
                public void accept(long t) {
                    super.accept(t);
                }
            };
            // Use reflection to set the downstream sink to our dummy (since accept is protected)
            // Alternatively, invoke accept directly if accessible
            pipeline.downstream = sink;
            long testValue = 42L;
            pipeline.accept(testValue);
            System.out.println("Accepted value: " + sink.received);
        } catch (Exception e) {
            System.err.println("Unexpected exception in testAcceptWithValidInput: " + e);
        }
    }

    private static void testAcceptWithNullSink() {
        try {
            // Create a pipeline with null downstream to simulate null sink
            LongPipeline<Long> pipeline = new LongPipeline<>(() -> null, 0, false) {
                @Override
                public void accept(long t) {
                    super.accept(t);
                }
            };
            pipeline.downstream = null;
            long testValue = 100L;
            pipeline.accept(testValue); // Should throw NullPointerException
            System.err.println("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception in testAcceptWithNullSink: " + e);
        }
    }
}
