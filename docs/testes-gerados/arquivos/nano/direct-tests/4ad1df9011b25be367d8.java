/* @test    @summary Test the update(byte) method of javax.crypto.Mac for normal and exceptional behavior    @run main MacWitupTest */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithInitializedMac();
        testUpdateWithoutInitialization();
    }

    private static void testUpdateWithInitializedMac() throws Exception {
        byte[] keyBytes = {1, 2, 3, 4};
        Key key = new SecretKeySpec(keyBytes, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        mac.update((byte) 0x0A);
        byte[] result = mac.doFinal();
        if (result == null || result.length == 0) {
            throw new AssertionError("Expected non-empty MAC result");
        }
    }

    private static void testUpdateWithoutInitialization() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Not initializing mac
            mac.update((byte) 0x0A);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
