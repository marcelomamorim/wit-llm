/*
 * @test
 * @summary Test Sink.ChainedLong constructor for null downstream argument
 * @run main SinkWitupTest
 */

import java.util.stream.Sink;
import java.util.Objects;

public class SinkWitupTest {

    // Minimal stub class to satisfy generic Sink parameter
    static class DummySink<T> implements Sink<T> {
        public void begin(long size) {}
        public void end() {}
        public boolean cancellationRequested() { return false; }
        public void accept(T t) {}
    }

    public static void main(String[] args) {
        testConstructorRejectsNull();
        testConstructorAcceptsNonNull();
    }

    private static void testConstructorRejectsNull() {
        try {
            new Sink.ChainedLong<>(null);
            throw new AssertionError("Expected NullPointerException for null downstream");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorAcceptsNonNull() {
        Sink<Long> dummy = new DummySink<Long>();
        Sink.ChainedLong<Long> chained = new Sink.ChainedLong<Long>(dummy);
        if (chained == null) {
            throw new AssertionError("Constructor returned null instance");
        }
    }
}
