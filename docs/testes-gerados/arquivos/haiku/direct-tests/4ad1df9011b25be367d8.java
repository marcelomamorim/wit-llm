/*
 * @test
 * @summary Verifies Mac.update(byte) throws IllegalStateException when not initialized and produces the known HMAC-SHA256 result when fed one byte at a time after init
 * @run main MacWitupTest
 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithoutInitThrows();
        testUpdateByteByByteMatchesKnownVector();
    }

    static void testUpdateWithoutInitThrows() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        boolean thrown = false;
        try {
            mac.update((byte) 'a');
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when update is called before init");
        }
    }

    static void testUpdateByteByByteMatchesKnownVector() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec("key".getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] msg = "The quick brown fox jumps over the lazy dog".getBytes(StandardCharsets.UTF_8);
        for (byte b : msg) {
            mac.update(b);
        }
        String hex = toHex(mac.doFinal());
        String expected = "f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997479dbc2d1a3cd8";
        if (!expected.equals(hex)) {
            throw new RuntimeException("Unexpected HMAC: " + hex);
        }
    }

    static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
