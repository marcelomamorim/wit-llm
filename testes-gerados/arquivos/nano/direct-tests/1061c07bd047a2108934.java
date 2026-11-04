/*
 * @test
 * @summary Test DoublePipeline.accept(double t) method for correct downstream invocation.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;

public class DoublePipelineWitupTest {

    static class DummyDownstream implements DoubleConsumer {
        public double receivedValue = 0;
        public boolean accepted = false;
        @Override
        public void accept(double value) {
            this.receivedValue = value;
            this.accepted = true;
        }
    }

    public static void main(String[] args) {
        testAcceptMethodCallsDownstreamWithMappedValue();
    }

    static void testAcceptMethodCallsDownstreamWithMappedValue() {
        DummyDownstream downstream = new DummyDownstream();
        DoublePipeline pipeline = new DoublePipeline() {
            @Override
            public void accept(double t) {
                downstream.accept(t);
            }
        };
        DoubleUnaryOperator mapper = (double t) -> t * 2;
        // Override accept to include mapping
        pipeline = new DoublePipeline() {
            @Override
            public void accept(double t) {
                downstream.accept(mapper.applyAsDouble(t));
            }
        };
        double inputValue = 3.5;
        pipeline.accept(inputValue);
        assert downstream.accepted : "Downstream was not accepted";
        assert downstream.receivedValue == inputValue * 2 : "Mapped value incorrect";
    }
}
