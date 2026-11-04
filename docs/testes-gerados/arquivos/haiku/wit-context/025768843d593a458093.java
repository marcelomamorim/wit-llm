/*
 * @test
 * @summary Witup-generated tests for PushbackInputStream.unread(int)
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testUnreadThenRead();
        testBufferFullThrowsIOException();
        testClosedStreamThrowsIOException();
        testLifoOrderWithLargerBuffer();
        testOnlyLowOrderByteIsPushedBack();
    }

    static void testUnreadThenRead() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {10}));
        in.unread(65);
        check(in.read() == 65, "pushed back byte should be read first");
        check(in.read() == 10, "underlying byte should follow");
        check(in.read() == -1, "stream should be at EOF");
    }

    static void testBufferFullThrowsIOException() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]));
        in.unread(1);
        boolean thrown = false;
        try {
            in.unread(2);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "unread on full pushback buffer should throw IOException");
        check(in.read() == 1, "failed unread must not alter buffered byte");
    }

    static void testClosedStreamThrowsIOException() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]));
        in.close();
        boolean thrown = false;
        try {
            in.unread(1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "unread on closed stream should throw IOException");
    }

    static void testLifoOrderWithLargerBuffer() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 3);
        in.unread(1);
        in.unread(2);
        in.unread(3);
        check(in.read() == 3, "last pushed byte should be read first");
        check(in.read() == 2, "second pushed byte should be read next");
        check(in.read() == 1, "first pushed byte should be read last");
        check(in.read() == -1, "stream should be at EOF");
    }

    static void testOnlyLowOrderByteIsPushedBack() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]));
        in.unread(0x141);
        check(in.read() == 0x41, "unread should store the low 8 bits of the argument");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
