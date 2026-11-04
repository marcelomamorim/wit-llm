/*
 * @test
 * @summary Test tryAdvance method of StreamSpliterators for DoubleConsumer
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.stream.StreamSpliterators;
import java.util.function.DoubleConsumer;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidDouble();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithValidDouble() {
        double value = 42.0;
        DoubleConsumer consumer = new DoubleConsumer() {
            boolean called = false;
            double received;
            @Override
            public void accept(double t) {
                called = true;
                received = t;
                assert t == value : "Received value does not match";
            }
        };
        boolean result = false;
        try {
            result = StreamSpliterators.tryAdvance(consumer, value);
        } catch (Throwable t) {
            throw new AssertionError("Exception thrown during tryAdvance with valid double", t);
        }
        assert result : "tryAdvance should return true";
    }

    static void testTryAdvanceWithNullConsumer() {
        boolean thrown = false;
        try {
            StreamSpliterators.tryAdvance(null, 0.0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        assert thrown : "NullPointerException expected when consumer is null";
    }
}

// Helper class to access the method, since the original method is package-private
class StreamSpliterators {
    public static boolean tryAdvance(java.util.function.DoubleConsumer action, double value) {
        return new java.util.stream.StreamSpliterators().tryAdvance(action, value);
    }
}