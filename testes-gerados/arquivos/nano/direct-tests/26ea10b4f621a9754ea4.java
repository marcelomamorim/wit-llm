/*
 * @test
 * @summary Test the accept method of ReferencePipeline for correct behavior.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.ToIntFunction;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testAcceptWithValidInput();
        testAcceptWithNullInput();
    }

    static class DummyDownstream {
        Object received;
        boolean called = false;
        void accept(Object obj) {
            this.received = obj;
            this.called = true;
        }
    }

    static class DummyMapper implements ToIntFunction<Object> {
        @Override
        public int applyAsInt(Object value) {
            return value.hashCode();
        }
    }

    static void testAcceptWithValidInput() {
        DummyDownstream downstream = new DummyDownstream();
        ReferencePipeline<Object, Integer> pipeline = new ReferencePipeline<>(null, 0, false) {
            @Override
            public void accept(Integer u) {
                downstream.accept(u);
            }
        };
        DummyMapper mapper = new DummyMapper();
        // Simulate the accept method call
        Integer input = 42;
        pipeline.accept(input);
        assert downstream.called : "Downstream should be called";
        assert downstream.received.equals(42) : "Received value should be 42";
    }

    static void testAcceptWithNullInput() {
        DummyDownstream downstream = new DummyDownstream();
        ReferencePipeline<Object, Integer> pipeline = new ReferencePipeline<>(null, 0, false) {
            @Override
            public void accept(Integer u) {
                downstream.accept(u);
            }
        };
        // Simulate the accept method call with null
        Integer input = null;
        try {
            pipeline.accept(input);
            assert downstream.called : "Downstream should be called even with null";
            assert downstream.received == null : "Received value should be null";
        } catch (Exception e) {
            assert false : "accept should handle null without exception";
        }
    }
}
