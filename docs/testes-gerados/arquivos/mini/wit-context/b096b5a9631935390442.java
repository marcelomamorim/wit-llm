/*
 * @test
 * @summary Test PushbackInputStream.read() behavior including IOException on closed stream and normal read
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadThrowsIOExceptionWhenClosed();
        testReadReturnsPushedBackByte();
        testReadReturnsByteFromUnderlyingStream();
    }

    private static void testReadThrowsIOExceptionWhenClosed() {
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {1, 2, 3}));
        try {
            pbis.close();
            pbis.read();
            throw new RuntimeException("Expected IOException not thrown after close");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testReadReturnsPushedBackByte() throws IOException {
        byte[] data = {10, 20, 30};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data), 2);
        int first = pbis.read(); // should read 10
        if (first != 10) {
            throw new RuntimeException("Expected first byte 10 but got " + first);
        }
        pbis.unread(99); // push back 99
        int pushedBack = pbis.read(); // should read 99
        if (pushedBack != 99) {
            throw new RuntimeException("Expected pushed back byte 99 but got " + pushedBack);
        }
        int next = pbis.read(); // should read 20
        if (next != 20) {
            throw new RuntimeException("Expected next byte 20 but got " + next);
        }
    }

    private static void testReadReturnsByteFromUnderlyingStream() throws IOException {
        byte[] data = {42};
        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data));
        int readByte = pbis.read();
        if (readByte != 42) {
            throw new RuntimeException("Expected byte 42 but got " + readByte);
        }
        int eof = pbis.read();
        if (eof != -1) {
            throw new RuntimeException("Expected EOF (-1) but got " + eof);
        }
    }
}
