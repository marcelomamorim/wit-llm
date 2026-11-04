/*
 * @test
 * @summary Behavioral tests for PushbackInputStream.unread(int) on JDK 11
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testUnreadSingleByteDefaultBuffer();
        testBufferFullThrowsIOException();
        testLifoOrderWithLargerBuffer();
        testOnlyLowEightBitsAreStored();
        testUnreadAfterCloseThrowsIOException();
    }

    static void testUnreadSingleByteDefaultBuffer() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {'A'}));
        int first = p.read();
        check(first == 'A', "first read should be 'A'");
        p.unread(first);
        check(p.read() == 'A', "unread byte should be returned by next read");
        check(p.read() == -1, "stream should be exhausted");
    }

    static void testBufferFullThrowsIOException() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]));
        p.unread(1);
        boolean thrown = false;
        try {
            p.unread(2);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "second unread on full 1-byte buffer should throw IOException");
        check(p.read() == 1, "first pushed-back byte should still be available");
    }

    static void testLifoOrderWithLargerBuffer() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 3);
        p.unread(1);
        p.unread(2);
        p.unread(3);
        check(p.read() == 3, "last pushed byte should be read first");
        check(p.read() == 2, "second pushed byte should be read next");
        check(p.read() == 1, "first pushed byte should be read last");
        check(p.read() == -1, "stream should be empty afterwards");
    }

    static void testOnlyLowEightBitsAreStored() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]));
        p.unread(0x1FF);
        check(p.read() == 0xFF, "unread should store only the low 8 bits");
    }

    static void testUnreadAfterCloseThrowsIOException() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]));
        p.close();
        boolean thrown = false;
        try {
            p.unread(1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "unread on closed stream should throw IOException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
