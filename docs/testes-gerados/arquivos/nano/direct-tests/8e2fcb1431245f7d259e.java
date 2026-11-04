/* @test    @summary Test StringCharBuffer.put(char c) method for ReadOnlyBufferException    @run main StringCharBufferWitupTest */

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public class StringCharBufferWitupTest {

    public static void main(String[] args) {
        testPutMethodThrowsReadOnlyBufferException();
    }

    public static void testPutMethodThrowsReadOnlyBufferException() {
        CharSequence sequence = "test";
        StringCharBuffer buffer = new StringCharBuffer(sequence, 0, sequence.length());
        try {
            buffer.put('a');
            // Se nenhuma exceção for lançada, o teste falha
            throw new AssertionError("Expected ReadOnlyBufferException was not thrown");
        } catch (ReadOnlyBufferException e) {
            // Exceção esperada, o teste passa
        }
    }
}
