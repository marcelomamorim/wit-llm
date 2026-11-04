/* @test    @summary Test for Sink.ChainedInt constructor behavior    @run main SinkWitupTest */

import java.util.stream.Sink;

public class SinkWitupTest {

    public static void main(String[] args) {
        testChainedIntConstructorWithValidDownstream();
        testChainedIntConstructorWithNullDownstream();
    }

    private static void testChainedIntConstructorWithValidDownstream() {
        Sink<Object> downstream = new Sink<Object>() {
            @Override
            public void accept(int value) {}
            @Override
            public void begin(long size) {}
            @Override
            public void end() {}
            @Override
            public boolean cancellationRequested() { return false; }
        };
        try {
            Sink.ChainedInt chained = new Sink.ChainedInt(downstream);
            assert chained != null : "ChainedInt instance should not be null";
        } catch (Exception e) {
            throw new AssertionError("Constructor threw an exception with valid downstream", e);
        }
    }

    private static void testChainedIntConstructorWithNullDownstream() {
        try {
            new Sink.ChainedInt(null);
            throw new AssertionError("Constructor should throw NullPointerException when downstream is null");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Constructor threw unexpected exception when downstream is null", e);
        }
    }
}
