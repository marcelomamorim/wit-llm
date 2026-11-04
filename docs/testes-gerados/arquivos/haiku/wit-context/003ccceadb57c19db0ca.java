/*
 * @test
 * @summary Tests observable behavior of PushbackInputStream.unread(byte[], int, int)
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testUnreadThenRead();
        testUnreadWithOffset();
        testUnreadOverflowThrowsAndKeepsBuffer();
        testUnreadOnClosedStreamThrows();
    }

    static void testUnreadThenRead() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[]{9}), 3);
        in.unread(new byte[]{1, 2}, 0, 2);
        check(in.read() == 1, "first unread byte");
        check(in.read() == 2, "second unread byte");
        check(in.read() == 9, "underlying byte");
        check(in.read() == -1, "end of stream");
    }

    static void testUnreadWithOffset() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 2);
        in.unread(new byte[]{5, 6, 7}, 1, 2);
        check(in.read() == 6, "offset first byte");
        check(in.read() == 7, "offset second byte");
        check(in.read() == -1, "empty after offset unread");
    }

    static void testUnreadOverflowThrowsAndKeepsBuffer() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 2);
        boolean thrown = false;
        try {
            in.unread(new byte[]{1, 2, 3}, 0, 3);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "overflow must throw IOException");
        in.unread(new byte[]{4}, 0, 1);
        check(in.read() == 4, "buffer intact after failed unread");
    }

    static void testUnreadOnClosedStreamThrows() throws IOException {
        PushbackInputStream in = new PushbackInputStream(
                new ByteArrayInputStream(new byte[]{1}), 1);
        in.close();
        boolean thrown = false;
        try {
            in.unread(new byte[]{2}, 0, 1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "unread on closed stream must throw IOException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
