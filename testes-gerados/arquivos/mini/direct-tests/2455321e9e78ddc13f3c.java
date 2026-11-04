/*
 * @test
 * @summary Test constructor of Sink.ChainedReference for null and non-null downstream
 * @run main SinkWitupTest
 */

import java.util.Objects;
import java.util.stream.Sink;

public class SinkWitupTest {

    // Minimal Sink implementation for testing
    static class DummySink<T> implements Sink<T> {
        public void begin(long size) {}
        public boolean cancellationRequested() { return false; }
        public void end() {}
        public void accept(T t) {}
    }

    public static void main(String[] args) {
        testNonNullDownstream();
        testNullDownstream();
    }

    private static void testNonNullDownstream() {
        DummySink<Object> downstream = new DummySink<>();
        Sink.ChainedReference<Object, Object> chained = new Sink.ChainedReference<>(downstream);
        if (chained.downstream != downstream) {
            throw new AssertionError("Downstream not set correctly");
        }
    }

    private static void testNullDownstream() {
        try {
            new Sink.ChainedReference<>(null);
            throw new AssertionError("Expected NullPointerException for null downstream");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
