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
        testAcceptWithDownstreamException();
    }

    private static void testAcceptNormal() {
        // Setup a downstream consumer that records invocation
        RecordingDoubleConsumer downstream = new RecordingDoubleConsumer();
        // Create an instance of DoublePipeline with minimal setup
        DoublePipeline<Double> pipeline = new DoublePipeline<Double>(null, 0, false) {
            @Override
            public void accept(double t) {
                downstream.accept(t);
            }
        };
        // Call accept with a normal value
        double testValue = 42.0;
        pipeline.accept(testValue);
        // Verify that downstream received the value
        if (downstream.lastValue != testValue) {
            throw new AssertionError("Downstream did not receive the correct value.");
        }
    }

    private static void testAcceptWithDownstreamException() {
        // Setup a downstream consumer that throws an exception
        DoubleConsumer throwingConsumer = new DoubleConsumer() {
            @Override
            public void accept(double value) {
                throw new IllegalStateException("Downstream exception") ;
            }
        };
        // Create an instance of DoublePipeline with minimal setup
        DoublePipeline<Double> pipeline = new DoublePipeline<Double>(null, 0, false) {
            @Override
            public void accept(double t) {
                downstream.accept(t);
            }
        };
        // Override accept to use the throwing consumer
        pipeline = new DoublePipeline<Double>(null, 0, false) {
            @Override
            public void accept(double t) {
                throwingConsumer.accept(t);
            }
        };
        // Call accept and expect an exception
        boolean exceptionThrown = false;
        try {
            pipeline.accept(1.23);
        } catch (IllegalStateException e) {
            exceptionThrown = true;
            if (!"Downstream exception".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message", e);
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected exception was not thrown.");
        }
    }

    private static class RecordingDoubleConsumer implements DoubleConsumer {
        double lastValue = Double.NaN;
        @Override
        public void accept(double value) {
            lastValue = value;
        }
    }
}
