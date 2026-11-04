/* @test    @summary Test the unread(byte[] b, int off, int len) method of PushbackInputStream    @run main PushbackInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.io.IOException;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testUnreadWithValidInput();
        testUnreadWithBufferFull();
        testUnreadWithEmptyBuffer();
    }

    static void testUnreadWithValidInput() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 5);
        byte[] buffer = {9, 8, 7};
        pbis.unread(buffer, 0, buffer.length);
        byte[] result = new byte[8];
        int readBytes = pbis.read(result, 0, result.length);
        boolean condition = (readBytes == 8) && (result[0] == 9) && (result[1] == 8) && (result[2] == 7);
        if (!condition) {
            throw new AssertionError("Failed testUnreadWithValidInput");
        }
    }

    static void testUnreadWithBufferFull() throws IOException {
        byte[] data = {1, 2};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 2);
        byte[] buffer = {1, 2};
        pbis.unread(buffer, 0, buffer.length);
        try {
            pbis.unread(buffer, 0, 1);
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!e.getMessage().equals("Push back buffer is full")) {
                throw e;
            }
        }
    }

    static void testUnreadWithEmptyBuffer() throws IOException {
        byte[] data = {10};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(bais, 1);
        byte[] buffer = {5};
        pbis.unread(buffer, 0, 1);
        int byteRead = pbis.read();
        if (byteRead != 5) {
            throw new AssertionError("Failed testUnreadWithEmptyBuffer");
        }
    }
}
