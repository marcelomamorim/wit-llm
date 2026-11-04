/*
 * @test
 * @summary Test for java.nio.CharBufferSpliterator.forEachRemaining(IntConsumer)
 * @run main CharBufferSpliteratorWitupTest
 */

import java.nio.CharBuffer;
import java.nio.CharBufferSpliterator;
import java.util.function.IntConsumer;

public class CharBufferSpliteratorWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingConsumesAll();
        testForEachRemainingEmptyBuffer();
    }

    private static void testForEachRemainingNullAction() {
        CharBuffer cb = CharBuffer.wrap(new char[] {'a', 'b', 'c'});
        CharBufferSpliterator spliterator = new CharBufferSpliterator(cb);
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingConsumesAll() {
        char[] data = {'x', 'y', 'z'};
        CharBuffer cb = CharBuffer.wrap(data);
        CharBufferSpliterator spliterator = new CharBufferSpliterator(cb);

        final StringBuilder sb = new StringBuilder();
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                sb.append((char) value);
            }
        };

        spliterator.forEachRemaining(consumer);

        if (!sb.toString().equals("xyz")) {
            throw new AssertionError("forEachRemaining did not consume all elements: " + sb.toString());
        }

        // After forEachRemaining, estimateSize should be zero
        if (spliterator.estimateSize() != 0) {
            throw new AssertionError("estimateSize should be 0 after forEachRemaining");
        }

        // Subsequent forEachRemaining calls should do nothing
        final boolean[] called = {false};
        spliterator.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                called[0] = true;
            }
        });
        if (called[0]) {
            throw new AssertionError("forEachRemaining called consumer after buffer exhausted");
        }
    }

    private static void testForEachRemainingEmptyBuffer() {
        CharBuffer cb = CharBuffer.allocate(0);
        CharBufferSpliterator spliterator = new CharBufferSpliterator(cb);

        final boolean[] called = {false};
        spliterator.forEachRemaining(new IntConsumer() {
            public void accept(int value) {
                called[0] = true;
            }
        });

        if (called[0]) {
            throw new AssertionError("forEachRemaining should not call consumer on empty buffer");
        }

        if (spliterator.estimateSize() != 0) {
            throw new AssertionError("estimateSize should be 0 for empty buffer");
        }
    }
}
