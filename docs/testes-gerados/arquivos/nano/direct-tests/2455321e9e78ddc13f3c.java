/* @test    @summary Test for Sink.ChainedReference constructor behavior    @run main SinkWitupTest */

import java.util.stream.Sink;
import java.util.Objects;

public class SinkWitupTest {

    public static void main(String[] args) {
        testChainedReferenceWithValidDownstream();
        testChainedReferenceWithNullDownstream();
    }

    private static void testChainedReferenceWithValidDownstream() {
        Sink<Object> downstream = new DummySink();
        Sink<Object>.ChainedReference chained = new Sink<Object>.ChainedReference(downstream);
        assert chained != null : "ChainedReference instance should not be null";
    }

    private static void testChainedReferenceWithNullDownstream() {
        try {
            new Sink<Object>.ChainedReference(null);
            assert false : "Expected NullPointerException when downstream is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static class DummySink implements Sink<Object> {
        @Override
        public void begin(long size) {}
        @Override
        public void accept(Object t) {}
        @Override
        public boolean cancellationRequested() { return false; }
        @Override
        public void end() {}
    }
}
