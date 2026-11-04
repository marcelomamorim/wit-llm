/*
 * @test
 * @summary Checks observable behavior of PushbackInputStream.read(): pushed-back bytes first, unsigned masking, EOF, and IOException after close
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testPushbackBytesReadFirst();
        testPushedBackByteIsMaskedToUnsigned();
        testEofAfterUnderlyingData();
        testReadAfterCloseThrowsIOException();
        testPushbackPendingBeforeCloseStillThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPushbackBytesReadFirst() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {1, 2}), 2);
        p.unread(9);
        check(p.read() == 9, "pushed back byte should be read first");
        check(p.read() == 1, "underlying byte 1 expected after pushback");
        check(p.read() == 2, "underlying byte 2 expected");
        check(p.read() == -1, "EOF expected");
    }

    static void testPushedBackByteIsMaskedToUnsigned() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 1);
        p.unread(0xFF);
        check(p.read() == 255, "read must return unsigned value 255");
    }

    static void testEofAfterUnderlyingData() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {5}));
        check(p.read() == 5, "first byte expected");
        check(p.read() == -1, "EOF expected after data consumed");
    }

    static void testReadAfterCloseThrowsIOException() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {1}));
        p.close();
        boolean thrown = false;
        try {
            p.read();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "read after close must throw IOException");
    }

    static void testPushbackPendingBeforeCloseStillThrows() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {1}), 2);
        p.unread(7);
        p.close();
        boolean thrown = false;
        try {
            p.read();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "read with pending pushback after close must throw IOException");
    }
}
