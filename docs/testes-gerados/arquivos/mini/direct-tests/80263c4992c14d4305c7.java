/*
 * @test
 * @summary Test PushbackInputStream.unread(byte[] b) method
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.io.IOException;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testUnreadWithValidArray();
        testUnreadWithEmptyArray();
    }

    private static void testUnreadWithValidArray() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 3);

        // Read one byte to move stream forward
        int first = pbis.read();
        if (first != 1) {
            throw new RuntimeException("Expected first byte 1, got " + first);
        }

        // Unread the bytes
        pbis.unread(new byte[] {1, 2, 3});

        // Read back the unread bytes
        int[] expected = {1, 2, 3};
        for (int i = 0; i < expected.length; i++) {
            int val = pbis.read();
            if (val != expected[i]) {
                throw new RuntimeException("Expected byte " + expected[i] + ", got " + val);
            }
        }

        // After reading all, next read should be -1 (end of stream)
        int eof = pbis.read();
        if (eof != -1) {
            throw new RuntimeException("Expected EOF (-1), got " + eof);
        }

        pbis.close();
    }

    private static void testUnreadWithEmptyArray() throws IOException {
        byte[] data = {10};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 1);

        // Unread empty array should not change stream
        pbis.unread(new byte[0]);

        int val = pbis.read();
        if (val != 10) {
            throw new RuntimeException("Expected byte 10, got " + val);
        }

        int eof = pbis.read();
        if (eof != -1) {
            throw new RuntimeException("Expected EOF (-1), got " + eof);
        }

        pbis.close();
    }
}
