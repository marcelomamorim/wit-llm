/*
 * @test
 * @summary Verify MessageDigest.update(ByteBuffer) null handling and basic digest behavior
 * @run main MessageDigestWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MessageDigestWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testValidUpdateProducesKnownDigest();
    }

    static void testNullInputThrowsNPE() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try {
            md.update((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
              toHex(md.digest()));
    }

    static void testValidUpdateProducesKnownDigest() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(ByteBuffer.wrap("abc".getBytes(StandardCharsets.US_ASCII)));
        check("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
              toHex(md.digest()));
    }

    static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
