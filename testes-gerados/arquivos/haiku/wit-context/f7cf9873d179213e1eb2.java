/*
 * @test
 * @summary Checks MessageDigest.update(byte[], int, int) argument validation and observable digest results
 * @run main MessageDigestWitupTest
 */

import java.security.MessageDigest;

public class MessageDigestWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsIAE();
        testInputTooShortThrowsIAE();
        testZeroLengthAtEndIsAccepted();
        testValidUpdateProducesExpectedDigest();
    }

    static void testNullInputThrowsIAE() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        expectIAE(md, null, 0, 0);
    }

    static void testInputTooShortThrowsIAE() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] in = new byte[3];
        expectIAE(md, in, 1, 3);
        expectIAE(md, in, 0, 4);
    }

    static void testZeroLengthAtEndIsAccepted() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] in = new byte[3];
        md.update(in, in.length, 0);
        String expected = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        check(expected, toHex(md.digest()));
    }

    static void testValidUpdateProducesExpectedDigest() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] in = "xabcx".getBytes("US-ASCII");
        md.update(in, 1, 3);
        String expected = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
        check(expected, toHex(md.digest()));
    }

    static void expectIAE(MessageDigest md, byte[] in, int off, int len) {
        try {
            md.update(in, off, len);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException");
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    static String toHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) {
            sb.append(String.format("%02x", x & 0xff));
        }
        return sb.toString();
    }
}
