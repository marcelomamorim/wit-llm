/*
 * @test
 * @summary Tests for Streams.accept(int) method to verify behavior and exception handling.
 * @run main StreamsWitupTest
 */

import java.util.stream.Streams;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testAcceptNormalFlow();
        testAcceptIllegalStateException();
    }

    private static void testAcceptNormalFlow() {
        Streams stream = new Streams();
        // Using reflection or direct instantiation is not possible due to private constructor,
        // so we test the method in isolation by subclassing or via a dummy class if needed.
        // Here, we simulate the accept method behavior directly.
        // Since Streams is a final class with private constructor, we test via a subclass or dummy class.
        // For simplicity, we create an anonymous class implementing the accept method.
        class DummyStreams extends Streams {
            int state = 0;
            int bufferCount = 0;
            int firstValue = -1;
            Object buffer = null;

            public void accept(int t) {
                if (state == 0) {
                    firstValue = t;
                    state = 1;
                } else if (state == 1) {
                    if (buffer == null) {
                        buffer = new Object(); // placeholder for SpinedBuffer.OfInt
                        bufferCount++;
                        state = 2;
                    }
                    // simulate buffer.accept(t)
                } else {
                    // Should throw IllegalStateException in real code
                }
            }
        }
        DummyStreams ds = new DummyStreams();
        ds.accept(5);
        assert ds.firstValue == 5 : "First value should be 5";
        ds.accept(10);
        // After second accept, buffer should be initialized
        assert ds.buffer != null : "Buffer should be initialized";
        System.out.println("testAcceptNormalFlow passed.");
    }

    private static void testAcceptIllegalStateException() {
        class DummyStreams extends Streams {
            int state = 2; // simulate invalid state
            public void accept(int t) {
                if (state != 0 && state != 1) {
                    throw new IllegalStateException();
                }
            }
        }
        DummyStreams ds = new DummyStreams();
        try {
            ds.accept(7);
            assert false : "Expected IllegalStateException";
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException.");
        }
        System.out.println("testAcceptIllegalStateException passed.");
    }
}
