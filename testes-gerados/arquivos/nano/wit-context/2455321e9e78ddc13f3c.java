/*
 * @test
 * @summary Tests for java.util.stream.Sink class, focusing on the ChainedReference constructor.
 * @run main SinkWitupTest
 */

import java.util.stream.Sink;
import java.util.Objects;

public class SinkWitupTest {

    public static void main(String[] args) {
        testChainedReferenceWithNullDownstream();
        testChainedReferenceWithValidDownstream();
    }

    private static void testChainedReferenceWithNullDownstream() {
        boolean exceptionThrown = false;
        try {
            new Sink.ChainedReference<Object>(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when downstream is null");
        }
    }

    private static void testChainedReferenceWithValidDownstream() {
        Sink<Object> downstreamSink = new Sink<Object>() {
            @Override
            public void begin(long size) {}
            @Override
            public void accept(Object t) {}
            @Override
            public void end() {}
            @Override
            public boolean cancellationRequested() { return false; }
        };
        Sink.ChainedReference<Object> chainedRef = new Sink.ChainedReference<>(downstreamSink);
        if (chainedRef == null) {
            throw new AssertionError("ChainedReference instance should not be null");
        }
    }
}
