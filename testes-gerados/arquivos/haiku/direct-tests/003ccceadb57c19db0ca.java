/*
 * @test
 * @summary Verifies observable behavior of PushbackInputStream.unread(byte[], int, int)
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testUnreadThenReadPreservesOrder();
        testOffsetSelectsSubRange();
        testZeroLengthIsNoOp();
        testExactFillSucceeds();
        testOverflowThrowsIOException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUnreadThenReadPreservesOrder() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {1}), 4);
        p.unread(new byte[] {10, 20, 30}, 0, 3);
        check(p.read() == 10, "first pushed byte mismatch");
        check(p.read() == 20, "second pushed byte mismatch");
        check(p.read() == 30, "third pushed byte mismatch");
        check(p.read() == 1, "underlying byte mismatch");
        check(p.read() == -1, "expected end of stream");
    }

    static void testOffsetSelectsSubRange() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 4);
        byte[] b = {9, 8, 7, 6};
        p.unread(b, 1, 2);
        check(p.read() == 8, "offset read mismatch at first byte");
        check(p.read() == 7, "offset read mismatch at second byte");
        check(p.read() == -1, "expected end of stream after offset unread");
    }

    static void testZeroLengthIsNoOp() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {5}), 1);
        p.unread(new byte[] {1}, 0, 0);
        check(p.read() == 5, "zero-length unread must not alter stream");
    }

    static void testExactFillSucceeds() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 3);
        p.unread(new byte[] {1, 2, 3}, 0, 3);
        check(p.read() == 1, "exact fill: byte 1 mismatch");
        check(p.read() == 2, "exact fill: byte 2 mismatch");
        check(p.read() == 3, "exact fill: byte 3 mismatch");
    }

    static void testOverflowThrowsIOException() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 2);
        p.unread(new byte[] {1, 2}, 0, 2);
        boolean thrown = false;
        try {
            p.unread(new byte[] {3}, 0, 1);
        } catch (IOException e) {
            thrown = true;
            check("Push back buffer is full".equals(e.getMessage()),
                    "unexpected overflow message: " + e.getMessage());
        }
        check(thrown, "expected IOException when pushback buffer is full");
    }
}
