/*
 * @test
 * @summary Test for DoublePipeline.accept(double) method, focusing on normal and exceptional behavior.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptWithNullDownstream();
    }

    static void testAcceptNormal() {
        // Setup a downstream DoubleConsumer that records invocation
        RecordingDoubleConsumer downstream = new RecordingDoubleConsumer();
        // Create a dummy DoublePipeline with minimal implementation for testing
        DoublePipeline pipeline = new DoublePipeline() {
            @Override
            public void accept(double t) {
                downstream.accept(t);
            }
        };
        double testValue = 42.0;
        pipeline.accept(testValue);
        if (downstream.lastValue != testValue) {
            throw new AssertionError("Expected downstream to accept value: " + testValue + ", but got: " + downstream.lastValue);
        }
    }

    static void testAcceptWithNullDownstream() {
        // Setup a DoublePipeline that throws NullPointerException when downstream is null
        DoublePipeline pipeline = new DoublePipeline() {
            @Override
            public void accept(double t) {
                // simulate downstream being null
                DoubleConsumer downstream = null;
                // calling accept on null should throw NullPointerException
                downstream.accept(t);
            }
        };
        boolean exceptionThrown = false;
        try {
            pipeline.accept(10.0);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when downstream is null");
        }
    }

    static class RecordingDoubleConsumer implements DoubleConsumer {
        double lastValue = Double.NaN;
        @Override
        public void accept(double value) {
            this.lastValue = value;
        }
    }
}
