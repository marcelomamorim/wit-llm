/*
 * @test
 * @summary Test tryAdvance method of StreamSpliterators for correct behavior
 * @run main StreamSpliteratorsWitupTest
 */
import java.util.stream.StreamSpliterators;
import java.util.function.Consumer;
import java.util.Objects;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNonNullConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenBufferEmpty();
    }

    static void testTryAdvanceWithNonNullConsumer() {
        // Create a dummy buffer with a single element
        DummyBuffer buffer = new DummyBuffer();
        buffer.add("element1");
        // Create a dummy StreamSpliterators instance with overridden doAdvance()
        StreamSpliterators instance = new StreamSpliterators() {
            @Override
            public boolean doAdvance() {
                // Simulate that buffer has elements
                return buffer.hasNext();
            }
        };
        // Set buffer in the instance
        instance.buffer = buffer;
        // Use a consumer that records accepted elements
        StringBuilder accepted = new StringBuilder();
        Consumer<String> consumer = s -> accepted.append(s);
        boolean result = instance.tryAdvance(consumer);
        assert result : "Expected tryAdvance to return true when buffer has elements";
        assert "element1".equals(accepted.toString()) : "Consumer should accept the element";
    }

    static void testTryAdvanceWithNullConsumer() {
        try {
            StreamSpliterators instance = new StreamSpliterators() {
                @Override
                public boolean doAdvance() {
                    return false;
                }
            };
            instance.tryAdvance(null);
            assert false : "Expected NullPointerException when consumer is null";
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testTryAdvanceWhenBufferEmpty() {
        // Create a dummy buffer with no elements
        DummyBuffer buffer = new DummyBuffer();
        // Create an instance with doAdvance() returning false
        StreamSpliterators instance = new StreamSpliterators() {
            @Override
            public boolean doAdvance() {
                return false;
            }
        };
        instance.buffer = buffer;
        StringBuilder accepted = new StringBuilder();
        Consumer<String> consumer = s -> accepted.append(s);
        boolean result = instance.tryAdvance(consumer);
        assert !result : "Expected tryAdvance to return false when doAdvance() is false";
        assert accepted.length() == 0 : "Consumer should not accept any element";
    }

    // Dummy buffer class for testing
    static class DummyBuffer {
        private java.util.List<String> elements = new java.util.ArrayList<>();
        void add(String s) { elements.add(s); }
        boolean hasNext() { return !elements.isEmpty(); }
        String get(int index) { return elements.get(index); }
    }
}
