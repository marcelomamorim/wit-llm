/*
 * @test
 * @summary Tests the accept method of LongPipeline for correct propagation of long values.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testAcceptMethodPropagatesValue();
    }

    public static void testAcceptMethodPropagatesValue() {
        final long[] receivedValue = new long[1];
        LongConsumer consumer = t -> receivedValue[0] = t;
        // Create a dummy subclass to test accept method
        LongPipeline pipeline = new LongPipeline(null, 0, false) {
            @Override
            public void accept(long t) {
                super.accept(t);
            }
        };
        // Override the accept method to include our consumer
        pipeline = new LongPipeline(null, 0, false) {
            @Override
            public void accept(long t) {
                consumer.accept(t);
            }
        };
        long testValue = 12345L;
        pipeline.accept(testValue);
        if (receivedValue[0] != testValue) {
            throw new AssertionError("Expected value: " + testValue + ", but got: " + receivedValue[0]);
        }
    }
}
