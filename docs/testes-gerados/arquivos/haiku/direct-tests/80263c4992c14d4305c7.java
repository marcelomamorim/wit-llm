/*
 * @test
 * @summary Tests observable behavior of PushbackInputStream.unread(byte[])
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testUnreadThenReadInOrder() throws IOException {
        PushbackInputStream pis = new PushbackInputStream(
                new ByteArrayInputStream(new byte[]{9}), 4);
        pis.unread(new byte[]{1, 2, 3});
        check(pis.read() == 1, "expected 1 first");
        check(pis.read() == 2, "expected 2 second");
        check(pis.read() == 3, "expected 3 third");
        check(pis.read() == 9, "expected underlying byte after pushback");
        check(pis.read() == -1, "expected EOF");
    }

    static void testUnreadEmptyArrayHasNoEffect() throws IOException {
        PushbackInputStream pis = new PushbackInputStream(
                new ByteArrayInputStream(new byte[]{5, 6}), 2);
        pis.unread(new byte[0]);
        check(pis.read() == 5, "empty unread must not alter stream");
        check(pis.read() == 6, "second byte expected");
        check(pis.read() == -1, "EOF expected");
    }

    static void testLifoOrderAcrossTwoUnreads() throws IOException {
        PushbackInputStream pis = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 3);
        pis.unread(new byte[]{1, 2});
        pis.unread(new byte[]{3});
        check(pis.read() == 3, "most recent unread must come first");
        check(pis.read() == 1, "then 1");
        check(pis.read() == 2, "then 2");
        check(pis.read() == -1, "EOF expected");
    }

    static void testOverflowThrowsIOException() {
        PushbackInputStream pis = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 2);
        boolean thrown = false;
        try {
            pis.unread(new byte[]{1, 2, 3});
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "unread beyond buffer size must throw IOException");
    }

    static void testUnreadOnClosedStreamThrowsIOException() throws IOException {
        PushbackInputStream pis = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 2);
        pis.close();
        boolean thrown = false;
        try {
            pis.unread(new byte[]{1});
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "unread on closed stream must throw IOException");
    }

    static void testNullArrayThrowsNullPointerException() throws IOException {
        PushbackInputStream pis = new PushbackInputStream(
                new ByteArrayInputStream(new byte[0]), 2);
        boolean thrown = false;
        try {
            pis.unread((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null array must throw NullPointerException");
    }

    public static void main(String[] args) throws Exception {
        testUnreadThenReadInOrder();
        testUnreadEmptyArrayHasNoEffect();
        testLifoOrderAcrossTwoUnreads();
        testOverflowThrowsIOException();
        testUnreadOnClosedStreamThrowsIOException();
        testNullArrayThrowsNullPointerException();
    }
}
