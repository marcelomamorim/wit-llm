/*
 * @test
 * @summary Test for StreamSpliterators forEachRemaining method
 * @run main StreamSpliteratorsWitupTest
 */
import java.util.stream.StreamSpliterators;
import java.util.function.IntConsumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithEmptyBuffer();
        testForEachRemainingNormalOperation();
    }

    static void testForEachRemainingWithNullConsumer() {
        try {
            StreamSpliterators.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithEmptyBuffer() {
        // Create a dummy instance with minimal setup
        class DummyStreamSpliterators extends StreamSpliterators {
            boolean initCalled = false;
            boolean tryAdvanceCalled = false;
            boolean wrapAndCopyIntoCalled = false;
            boolean finished = false;
            Object buffer = null;
            Object spliterator = null;
            Object ph = null;
            Object bufferSink = null;
            Object pusher = null;
            long nextToConsume = 0;

            void init() {
                initCalled = true;
            }

            boolean tryAdvance(IntConsumer consumer) {
                tryAdvanceCalled = true;
                return false;
            }

            void wrapAndCopyInto(IntConsumer consumer, Object spliterator) {
                wrapAndCopyIntoCalled = true;
            }
        }
        DummyStreamSpliterators dummy = new DummyStreamSpliterators();
        dummy.buffer = null; // empty buffer
        dummy.finished = false;
        // Call method
        dummy.forEachRemaining((IntConsumer) c -> {
            throw new AssertionError("Should not be called");
        });
        // Verify that init was called
        if (!dummy.initCalled) {
            throw new AssertionError("init() was not called");
        }
        // Verify that wrapAndCopyInto was called
        if (!dummy.wrapAndCopyIntoCalled) {
            throw new AssertionError("wrapAndCopyInto() was not called");
        }
    }

    static void testForEachRemainingNormalOperation() {
        class DummyStreamSpliterators extends StreamSpliterators {
            boolean initCalled = false;
            boolean tryAdvanceCalled = false;
            boolean wrapAndCopyIntoCalled = false;
            boolean finished = false;
            Object buffer = new Object();
            Object spliterator = new Object();
            Object ph = new Object();
            Object bufferSink = new Object();
            Object pusher = new Object();
            long nextToConsume = 0;

            void init() {
                initCalled = true;
            }

            boolean tryAdvance(IntConsumer consumer) {
                tryAdvanceCalled = true;
                consumer.accept(42);
                return true;
            }

            void wrapAndCopyInto(IntConsumer consumer, Object spliterator) {
                wrapAndCopyIntoCalled = true;
            }
        }
        DummyStreamSpliterators dummy = new DummyStreamSpliterators();
        dummy.buffer = new Object();
        dummy.finished = false;
        final int[] received = {0};
        dummy.forEachRemaining((IntConsumer) c -> {
            received[0] = c;
        });
        if (!dummy.tryAdvanceCalled) {
            throw new AssertionError("tryAdvance() was not called");
        }
        if (received[0] != 42) {
            throw new AssertionError("Consumer was not called with expected value");
        }
    }
}
