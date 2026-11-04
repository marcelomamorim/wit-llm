/*
 * @test
 * @summary Test DoublePipeline.accept(double) method for correct invocation of action and downstream.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    static class DummyConsumer implements DoubleConsumer {
        boolean accepted = false;
        double receivedValue = 0.0;
        @Override
        public void accept(double t) {
            accepted = true;
            receivedValue = t;
        }
    }

    public static void main(String[] args) {
        DummyConsumer action = new DummyConsumer();
        DummyConsumer downstream = new DummyConsumer();
        DoublePipeline pipeline = new DoublePipeline() {
            @Override
            public void accept(double t) {
                action.accept(t);
                downstream.accept(t);
            }
        };
        double testValue = 42.0;
        pipeline.accept(testValue);
        assert action.accepted : "Action consumer was not invoked";
        assert downstream.accepted : "Downstream consumer was not invoked";
        assert action.receivedValue == testValue : "Action consumer received wrong value";
        assert downstream.receivedValue == testValue : "Downstream consumer received wrong value";
    }
}
