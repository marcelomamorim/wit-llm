/*
 * @test
 * @summary Test StreamSpliterators.WrappingSpliterator.tryAdvance behavior
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.StreamSpliterators;

public class StreamSpliteratorsWitupTest {

    // Minimal concrete subclass to test tryAdvance
    static class TestWrappingSpliterator extends StreamSpliterators.AbstractWrappingSpliterator<Object, Object, Object> {

        TestWrappingSpliterator() {
            super(null, false, null);
            // Provide minimal buffer and nextToConsume to avoid NPE
            this.buffer = new Object() {
                Object get(long index) { return "element"; }
            };
            this.nextToConsume = 0L;
        }

        // doAdvance returns true to simulate element availability
        boolean doAdvance() {
            return true;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceNullConsumer();
        testTryAdvanceWithConsumer();
    }

    private static void testTryAdvanceNullConsumer() {
        TestWrappingSpliterator spliterator = new TestWrappingSpliterator();
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceWithConsumer() {
        TestWrappingSpliterator spliterator = new TestWrappingSpliterator();
        final boolean[] called = {false};
        Consumer<Object> consumer = new Consumer<Object>() {
            public void accept(Object t) {
                if ("element".equals(t)) {
                    called[0] = true;
                }
            }
        };
        boolean result = spliterator.tryAdvance(consumer);
        if (!result) {
            throw new RuntimeException("tryAdvance should return true when element is available");
        }
        if (!called[0]) {
            throw new RuntimeException("Consumer was not called with expected element");
        }
    }
}
