/* @test    @summary Test for Sink.ChainedLong constructor behavior    @run main SinkWitupTest */

import java.util.stream.Sink;

public class SinkWitupTest {

    public static void main(String[] args) {
        testChainedLongConstructorWithValidDownstream();
        testChainedLongConstructorWithNullDownstream();
    }

    private static void testChainedLongConstructorWithValidDownstream() {
        Sink<Object> downstreamSink = new Sink<Object>() {
            @Override
            public void accept(Object t) {}
            @Override
            public void begin(long size) {}
            @Override
            public void end() {}
            @Override
            public boolean cancellationRequested() { return false; }
        };
        try {
            Sink.ChainedLong<Object> chained = new Sink.ChainedLong<>(downstreamSink);
            assert chained != null : "ChainedLong instance should not be null";
        } catch (Exception e) {
            throw new AssertionError("Constructor threw an exception with valid downstream", e);
        }
    }

    private static void testChainedLongConstructorWithNullDownstream() {
        try {
            new Sink.ChainedLong<Object>(null);
            throw new AssertionError("Constructor should throw NullPointerException when downstream is null");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Constructor threw unexpected exception when downstream is null", e);
        }
    }
}
