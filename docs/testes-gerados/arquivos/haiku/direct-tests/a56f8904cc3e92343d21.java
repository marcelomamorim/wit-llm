/*
 * @test
 * @summary Basic observable behavior of Base64.Decoder.decode(byte[], byte[])
 * @run main Base64WitupTest
 */

import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testExactOutput();
        testPaddedOutput();
        testEmptyInput();
        testLargerDestination();
        testDestinationTooSmall();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testExactOutput() {
        byte[] src = "QUJD".getBytes();
        byte[] dst = new byte[3];
        int n = Base64.getDecoder().decode(src, dst);
        check(n == 3, "expected 3 bytes decoded, got " + n);
        check(dst[0] == 'A' && dst[1] == 'B' && dst[2] == 'C', "unexpected content");
    }

    static void testPaddedOutput() {
        byte[] src = "QQ==".getBytes();
        byte[] dst = new byte[1];
        int n = Base64.getDecoder().decode(src, dst);
        check(n == 1, "expected 1 byte decoded, got " + n);
        check(dst[0] == 'A', "unexpected content for padded input");
    }

    static void testEmptyInput() {
        byte[] src = new byte[0];
        byte[] dst = new byte[0];
        int n = Base64.getDecoder().decode(src, dst);
        check(n == 0, "expected 0 bytes decoded for empty input, got " + n);
    }

    static void testLargerDestination() {
        byte[] src = "QUJD".getBytes();
        byte[] dst = new byte[8];
        int n = Base64.getDecoder().decode(src, dst);
        check(n == 3, "expected 3 bytes decoded into larger buffer, got " + n);
        check(dst[0] == 'A' && dst[1] == 'B' && dst[2] == 'C', "unexpected content in larger buffer");
    }

    static void testDestinationTooSmall() {
        byte[] src = "QUJD".getBytes();
        byte[] dst = new byte[2];
        boolean thrown = false;
        try {
            Base64.getDecoder().decode(src, dst);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for too-small destination");
    }
}
