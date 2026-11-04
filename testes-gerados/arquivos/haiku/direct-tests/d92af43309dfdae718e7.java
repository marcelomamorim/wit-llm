/*
 * @test
 * @summary Behavioral tests for javax.crypto.Mac.update(ByteBuffer)
 * @run main MacWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateBeforeInitThrowsISE();
        testUpdateNullAfterInitThrowsIAE();
        testUpdateByteBufferKnownVector();
        testUpdateByteBufferMatchesByteArray();
        testUpdateConsumesBuffer();
    }

    static Mac newInitializedMac() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec("key".getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return mac;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    static void testUpdateBeforeInitThrowsISE() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.update(ByteBuffer.allocate(0));
            throw new RuntimeException("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testUpdateNullAfterInitThrowsIAE() throws Exception {
        Mac mac = newInitializedMac();
        try {
            mac.update((ByteBuffer) null);
            throw new RuntimeException("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testUpdateByteBufferKnownVector() throws Exception {
        Mac mac = newInitializedMac();
        byte[] msg = "The quick brown fox jumps over the lazy dog"
                .getBytes(StandardCharsets.UTF_8);
        mac.update(ByteBuffer.wrap(msg));
        String result = hex(mac.doFinal());
        check("f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997479dbc2d1a3cd8"
                .equals(result), "Unexpected HMAC: " + result);
    }

    static void testUpdateByteBufferMatchesByteArray() throws Exception {
        byte[] msg = "abcdefgh".getBytes(StandardCharsets.UTF_8);

        Mac macBuffer = newInitializedMac();
        macBuffer.update(ByteBuffer.wrap(msg));
        byte[] fromBuffer = macBuffer.doFinal();

        Mac macArray = newInitializedMac();
        macArray.update(msg);
        byte[] fromArray = macArray.doFinal();

        check(hex(fromBuffer).equals(hex(fromArray)),
                "ByteBuffer and byte[] updates differ");
    }

    static void testUpdateConsumesBuffer() throws Exception {
        Mac mac = newInitializedMac();
        ByteBuffer bb = ByteBuffer.wrap("consume".getBytes(StandardCharsets.UTF_8));
        mac.update(bb);
        check(bb.remaining() == 0, "Buffer was not consumed by update");
    }
}
