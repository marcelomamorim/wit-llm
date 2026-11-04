/*
 * @test
 * @summary Test for java.nio.CharBufferSpliterator.forEachRemaining(IntConsumer) method
 * @run main CharBufferSpliteratorWitupTest
 */

import java.nio.CharBuffer;
import java.nio.CharBufferSpliterator;
import java.util.function.IntConsumer;

public class CharBufferSpliteratorWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingConsumesAll();
    }

    private static void testForEachRemainingNullAction() {
        CharBuffer cb = CharBuffer.wrap(new char[] {'a', 'b', 'c'});
        CharBufferSpliterator spliterator = new CharBufferSpliterator(cb);
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
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
            throw new RuntimeException("forEachRemaining did not consume all elements correctly: " + sb.toString());
        }

        // After consumption, estimateSize should be zero
        if (spliterator.estimateSize() != 0) {
            throw new RuntimeException("estimateSize after forEachRemaining should be 0 but was " + spliterator.estimateSize());
        }

        // tryAdvance should return false after forEachRemaining
        boolean advanced = spliterator.tryAdvance(consumer);
        if (advanced) {
            throw new RuntimeException("tryAdvance should return false after forEachRemaining");
        }
    }
}
