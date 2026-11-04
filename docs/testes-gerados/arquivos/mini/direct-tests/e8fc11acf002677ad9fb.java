/*
 * @test
 * @summary Test Sink.ChainedLong constructor for null and non-null downstream
 * @run main SinkWitupTest
 */

import java.util.Objects;
import java.util.stream.Sink;

public class SinkWitupTest {

    static class DummySink implements Sink<Long> {
        public void begin(long size) {}
        public boolean cancellationRequested() { return false; }
        public void end() {}
        public void accept(Long t) {}
    }

    public static void main(String[] args) {
        // Test constructor with non-null downstream
        DummySink ds = new DummySink();
        Sink.ChainedLong<Long> chained = new Sink.ChainedLong<Long>(ds);
        if (chained == null) throw new AssertionError("ChainedLong instance is null");

        // Test constructor with null downstream - expect NullPointerException
        try {
            new Sink.ChainedLong<Long>(null);
            throw new AssertionError("Expected NullPointerException for null downstream");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
