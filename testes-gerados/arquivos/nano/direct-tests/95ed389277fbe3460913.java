/* @test    @summary Test for CharBufferSpliterator.forEachRemaining method    @run main CharBufferSpliteratorWitupTest */

import java.nio.CharBuffer;
import java.util.function.IntConsumer;

public class CharBufferSpliteratorWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidInput();
        testForEachRemainingWithNullAction();
        testForEachRemainingWithEmptyBuffer();
    }

    static void testForEachRemainingWithValidInput() {
        CharBuffer buffer = CharBuffer.allocate(5);
        buffer.put(new char[] { 'a', 'b', 'c', 'd', 'e' });
        buffer.flip();
        CharBufferSpliterator spliterator = new CharBufferSpliterator(buffer);
        StringBuilder result = new StringBuilder();
        spliterator.forEachRemaining(new IntConsumer() {
            @Override
            public void accept(int value) {
                result.append((char) value);
            }
        });
        assert result.toString().equals("abcde") : "Expected 'abcde', got '" + result.toString() + "'";
    }

    static void testForEachRemainingWithNullAction() {
        CharBuffer buffer = CharBuffer.allocate(3);
        buffer.put(new char[] { 'x', 'y', 'z' });
        buffer.flip();
        CharBufferSpliterator spliterator = new CharBufferSpliterator(buffer);
        try {
            spliterator.forEachRemaining(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testForEachRemainingWithEmptyBuffer() {
        CharBuffer buffer = CharBuffer.allocate(0);
        buffer.flip();
        CharBufferSpliterator spliterator = new CharBufferSpliterator(buffer);
        StringBuilder result = new StringBuilder();
        spliterator.forEachRemaining(new IntConsumer() {
            @Override
            public void accept(int value) {
                result.append((char) value);
            }
        });
        assert result.toString().equals("") : "Expected empty string, got '" + result.toString() + "'";
    }
}
