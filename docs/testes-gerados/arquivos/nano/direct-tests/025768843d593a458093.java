/* @test    @summary Test the unread(int b) method of PushbackInputStream for correct behavior and exception handling.    @run main PushbackInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.io.IOException;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testUnreadNormal();
        testUnreadBufferFull();
        testUnreadAfterClose();
    }

    static void testUnreadNormal() {
        byte[] data = {65, 66, 67}; // ASCII for A, B, C
        try (PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 2)) {
            int readByte = pbis.read(); // read first byte
            pbis.unread(readByte); // push back the same byte
            int rereadByte = pbis.read(); // should be the same as readByte
            if (readByte != rereadByte) {
                throw new AssertionError("Expected byte: " + readByte + ", but got: " + rereadByte);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testUnreadNormal", e);
        }
    }

    static void testUnreadBufferFull() {
        byte[] data = {1, 2};
        try (PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 1)) {
            pbis.read(); // read first byte
            pbis.read(); // read second byte, buffer is now full
            // Attempt to unread should throw IOException
            boolean exceptionThrown = false;
            try {
                pbis.unread(99);
            } catch (IOException e) {
                exceptionThrown = true;
                if (!"Push back buffer is full".equals(e.getMessage())) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage());
                }
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IOException when buffer is full, but none was thrown.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testUnreadBufferFull", e);
        }
    }

    static void testUnreadAfterClose() {
        byte[] data = {10, 20};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 1);
        try {
            pbis.close();
            boolean exceptionThrown = false;
            try {
                pbis.unread(5);
            } catch (IOException e) {
                exceptionThrown = true;
                if (!"Stream closed".equals(e.getMessage())) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage());
                }
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IOException after close, but none was thrown.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testUnreadAfterClose", e);
        }
    }
}
