/*
 * @test
 * @summary Tests for java.util.stream.Sink class, focusing on constructor behavior.
 * @run main SinkWitupTest
 */

import java.util.stream.Sink;
import java.util.Objects;

public class SinkWitupTest {

    public static void main(String[] args) {
        testChainedLongWithNullDownstream();
        testChainedLongWithValidDownstream();
    }

    private static void testChainedLongWithNullDownstream() {
        boolean exceptionThrown = false;
        try {
            new Sink.ChainedLong(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when downstream is null");
        }
    }

    private static void testChainedLongWithValidDownstream() {
        Sink<Object> downstreamSink = new Sink<Object>() {
            @Override
            public void begin(long size) {}
            @Override
            public void accept(Object t) {}
            @Override
            public void end() {}
        };
        Sink.ChainedLong<Object> chainedLong = new Sink.ChainedLong<>(downstreamSink);
        if (chainedLong == null) {
            throw new AssertionError("ChainedLong instance should not be null");
        }
    }
}
