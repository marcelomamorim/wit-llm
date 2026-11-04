/*
 * @test
 * @summary Test constructor of Sink.ChainedInt for null and non-null downstream
 * @run main SinkWitupTest
 */

import java.util.Objects;
import java.util.stream.Sink;

public class SinkWitupTest {

    static class DummySink implements Sink<Integer> {
        public void begin(long size) {}
        public void end() {}
        public boolean cancellationRequested() { return false; }
        public void accept(Integer i) {}
    }

    public static void main(String[] args) {
        // Test non-null downstream
        DummySink ds = new DummySink();
        Sink.ChainedInt<DummySink, Integer> chained = new Sink.ChainedInt<DummySink, Integer>(ds);
        if (chained.downstream != ds) {
            throw new AssertionError("downstream field not set correctly");
        }

        // Test null downstream throws NullPointerException
        try {
            new Sink.ChainedInt<DummySink, Integer>(null);
            throw new AssertionError("Expected NullPointerException for null downstream");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
