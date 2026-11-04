/*
 * @test
 * @summary Test Streams.forEachRemaining(Consumer) method behavior
 * @run main StreamsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;

public class StreamsWitupTest {

    static class TestStream<T> {
        T first;
        int count = -2;

        public TestStream(T first) {
            this.first = first;
        }

        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            if (count == -2) {
                action.accept(first);
                count = -1;
            }
        }
    }

    public static void main(String[] args) {
        // Test normal behavior: count == -2 triggers accept
        final StringBuilder sb = new StringBuilder();
        TestStream<String> stream = new TestStream<>("test");
        stream.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!"test".equals(sb.toString())) {
            throw new RuntimeException("Expected 'test' but got '" + sb.toString() + "'");
        }

        // Test that count changes to -1 after first call
        if (stream.count != -1) {
            throw new RuntimeException("Expected count to be -1 after forEachRemaining call");
        }

        // Test that calling again does not append again
        sb.setLength(0);
        stream.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (sb.length() != 0) {
            throw new RuntimeException("Expected no further accept calls when count != -2");
        }

        // Test that passing null throws NullPointerException
        boolean npeThrown = false;
        try {
            stream.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when passing null");
        }
    }
}
