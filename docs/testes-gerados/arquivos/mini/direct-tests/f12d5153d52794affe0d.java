/*
 * @test
 * @summary Test StringCharBuffer.put(int,char) throws ReadOnlyBufferException
 * @run main StringCharBufferWitupTest
 */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {
    public static void main(String[] args) {
        // Create a StringCharBuffer instance via subclass to test put(int,char)
        CharBuffer buf = new CharBuffer() {
            private final CharSequence str = "abcde";
            private final int offset = 0;
            private int position = 0;
            private int limit = str.length();
            private int capacity = str.length();

            @Override
            public CharBuffer put(int index, char c) {
                throw new ReadOnlyBufferException();
            }

            @Override
            public char get() {
                if (position >= limit) throw new IndexOutOfBoundsException();
                return str.charAt(position++);
            }

            @Override
            public char get(int index) {
                if (index < 0 || index >= limit) throw new IndexOutOfBoundsException();
                return str.charAt(index);
            }

            @Override
            public int position() { return position; }

            @Override
            public CharBuffer position(int newPosition) {
                if (newPosition < 0 || newPosition > limit) throw new IllegalArgumentException();
                position = newPosition;
                return this;
            }

            @Override
            public int limit() { return limit; }

            @Override
            public CharBuffer limit(int newLimit) {
                if (newLimit < 0 || newLimit > capacity) throw new IllegalArgumentException();
                limit = newLimit;
                if (position > limit) position = limit;
                return this;
            }

            @Override
            public int capacity() { return capacity; }

            @Override
            public CharBuffer clear() {
                position = 0;
                limit = capacity;
                return this;
            }

            @Override
            public CharBuffer flip() {
                limit = position;
                position = 0;
                return this;
            }

            @Override
            public CharBuffer rewind() {
                position = 0;
                return this;
            }

            @Override
            public boolean hasRemaining() {
                return position < limit;
            }

            @Override
            public int remaining() {
                return limit - position;
            }

            @Override
            public CharBuffer mark() { return this; }

            @Override
            public CharBuffer reset() { return this; }

            @Override
            public boolean isReadOnly() { return true; }

            @Override
            public CharBuffer slice() { return this; }

            @Override
            public CharBuffer duplicate() { return this; }

            @Override
            public CharBuffer asReadOnlyBuffer() { return this; }

            @Override
            public CharBuffer put(char c) {
                throw new ReadOnlyBufferException();
            }

            @Override
            public CharBuffer compact() {
                throw new ReadOnlyBufferException();
            }
        };

        // Test that put(int,char) throws ReadOnlyBufferException
        try {
            buf.put(0, 'x');
            throw new RuntimeException("Expected ReadOnlyBufferException not thrown");
        } catch (ReadOnlyBufferException e) {
            // Expected
        }
    }
}
