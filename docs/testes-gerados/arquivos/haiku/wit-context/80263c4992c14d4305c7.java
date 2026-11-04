/*
 * @test
 * @summary Witup-generated tests for PushbackInputStream.unread(byte[])
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        unreadArrayIsReadBackInOrder();
        unreadBytesComeBeforeUnderlyingBytes();
        unreadEmptyArrayIsNoOp();
        unreadOverflowThrowsIOException();
        unreadOverflowLeavesBufferUnchanged();
        unreadDefaultSizeOverflowThrowsIOException();
        unreadFullBufferThenAnotherThrowsIOException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void unreadArrayIsReadBackInOrder() throws IOException {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[0]), 4);
        p.unread(new byte[]{1, 2, 3});
        check(p.read() == 1, "expected 1");
        check(p.read() == 2, "expected 2");
        check(p.read() == 3, "expected 3");
        check(p.read() == -1, "expected EOF");
    }

    static void unreadBytesComeBeforeUnderlyingBytes() throws IOException {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[]{9}), 2);
        p.unread(new byte[]{5});
        check(p.read() == 5, "expected pushed byte first");
        check(p.read() == 9, "expected underlying byte second");
        check(p.read() == -1, "expected EOF");
    }

    static void unreadEmptyArrayIsNoOp() throws IOException {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[]{7}), 1);
        p.unread(new byte[0]);
        check(p.read() == 7, "empty unread must not alter stream");
        check(p.read() == -1, "expected EOF");
    }

    static void unreadOverflowThrowsIOException() {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[0]), 2);
        expectIOException(p, new byte[]{1, 2, 3});
    }

    static void unreadOverflowLeavesBufferUnchanged() throws IOException {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[]{4}), 2);
        expectIOException(p, new byte[]{1, 2, 3});
        check(p.read() == 4, "failed unread must not modify pushback buffer");
        check(p.read() == -1, "expected EOF");
    }

    static void unreadDefaultSizeOverflowThrowsIOException() {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[0]));
        expectIOException(p, new byte[]{1, 2});
    }

    static void unreadFullBufferThenAnotherThrowsIOException() throws IOException {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[0]), 1);
        p.unread(new byte[]{1});
        expectIOException(p, new byte[]{2});
        check(p.read() == 1, "expected first pushed byte to remain");
    }

    static void expectIOException(PushbackInputStream p, byte[] b) {
        try {
            p.unread(b);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException");
    }
}
