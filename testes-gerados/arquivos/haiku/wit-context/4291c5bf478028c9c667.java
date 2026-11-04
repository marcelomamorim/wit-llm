/*
 * @test
 * @summary Witup-derived tests for MessageDigest.digest(byte[], int, int) exception and output behavior
 * @run main MessageDigestWitupTest
 */

import java.security.MessageDigest;

public class MessageDigestWitupTest {

    private static final String ABC_SHA256 =
        "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
    private static final String EMPTY_SHA256 =
        "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    public static void main(String[] args) throws Exception {
        testNullBuffer();
        testBufferTooSmall();
        testBufferTooSmallWithOffset();
        testExactFitWithOffset();
        testResetAfterDigest();
    }

    static void testNullBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((byte) 'a');
        boolean thrown = false;
        try {
            md.digest(null, 0, 32);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for null buffer");
        }
    }

    static void testBufferTooSmall() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((byte) 'a');
        byte[] buf = new byte[31];
        boolean thrown = false;
        try {
            md.digest(buf, 0, 32);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for small buffer");
        }
    }

    static void testBufferTooSmallWithOffset() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((byte) 'a');
        byte[] buf = new byte[40];
        boolean thrown = false;
        try {
            md.digest(buf, 10, 32);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException when offset leaves too little space");
        }
    }

    static void testExactFitWithOffset() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((byte) 'a');
        md.update((byte) 'b');
        md.update((byte) 'c');
        byte[] buf = new byte[34];
        int n = md.digest(buf, 2, 32);
        if (n != 32) {
            throw new AssertionError("expected 32 bytes, got " + n);
        }
        if (buf[0] != 0 || buf[1] != 0) {
            throw new AssertionError("bytes before offset were modified");
        }
        byte[] expected = hexToBytes(ABC_SHA256);
        for (int i = 0; i < 32; i++) {
            if (buf[2 + i] != expected[i]) {
                throw new AssertionError("digest mismatch at index " + i);
            }
        }
    }

    static void testResetAfterDigest() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((byte) 'a');
        md.update((byte) 'b');
        md.update((byte) 'c');
        byte[] buf = new byte[32];
        md.digest(buf, 0, 32);
        byte[] empty = new byte[32];
        int n = md.digest(empty, 0, 32);
        if (n != 32) {
            throw new AssertionError("expected 32 bytes after reset, got " + n);
        }
        byte[] expectedEmpty = hexToBytes(EMPTY_SHA256);
        for (int i = 0; i < 32; i++) {
            if (empty[i] != expectedEmpty[i]) {
                throw new AssertionError("state not reset after digest at index " + i);
            }
        }
    }

    static byte[] hexToBytes(String hex) {
        byte[] out = new byte[hex.length() / 2];
        for (int i = 0; i < out.length; i++) {
            out[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return out;
    }
}
