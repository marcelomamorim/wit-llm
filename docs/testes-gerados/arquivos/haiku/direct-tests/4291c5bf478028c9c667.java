/*
 * @test
 * @summary Witup-style tests for MessageDigest.digest(byte[], int, int)
 * @run main MessageDigestWitupTest
 */

import java.security.MessageDigest;

public class MessageDigestWitupTest {

    static final String ABC_SHA256 =
        "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
    static final String EMPTY_SHA256 =
        "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    public static void main(String[] args) throws Exception {
        testNullBuffer();
        testBufferTooSmallForLength();
        testBufferTooSmallForOffset();
        testDigestWritesAtOffset();
        testStateResetAfterDigest();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static String hex(byte[] b, int off, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = off; i < off + len; i++) {
            sb.append(String.format("%02x", b[i] & 0xff));
        }
        return sb.toString();
    }

    static void testNullBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update("abc".getBytes("US-ASCII"));
        boolean thrown = false;
        try {
            md.digest(null, 0, 32);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for null buffer");
    }

    static void testBufferTooSmallForLength() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update("abc".getBytes("US-ASCII"));
        byte[] buf = new byte[10];
        boolean thrown = false;
        try {
            md.digest(buf, 0, 32);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException when buffer too small");
    }

    static void testBufferTooSmallForOffset() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update("abc".getBytes("US-ASCII"));
        byte[] buf = new byte[40];
        boolean thrown = false;
        try {
            md.digest(buf, 9, 32); // 40 - 9 = 31 < 32
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException when offset leaves too little space");
    }

    static void testDigestWritesAtOffset() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update("abc".getBytes("US-ASCII"));
        byte[] buf = new byte[40];
        int n = md.digest(buf, 4, 32);
        check(n == 32, "expected 32 bytes written, got " + n);
        check(ABC_SHA256.equals(hex(buf, 4, 32)), "digest bytes at offset 4 mismatch");
        for (int i = 0; i < 4; i++) {
            check(buf[i] == 0, "bytes before offset must be untouched");
        }
        for (int i = 36; i < 40; i++) {
            check(buf[i] == 0, "bytes after digest must be untouched");
        }
    }

    static void testStateResetAfterDigest() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update("abc".getBytes("US-ASCII"));
        byte[] buf = new byte[32];
        md.digest(buf, 0, 32);
        check(ABC_SHA256.equals(hex(buf, 0, 32)), "first digest mismatch");
        byte[] second = md.digest();
        check(EMPTY_SHA256.equals(hex(second, 0, second.length)),
              "digest state should be reset to empty input after digest(buf, off, len)");
    }
}
