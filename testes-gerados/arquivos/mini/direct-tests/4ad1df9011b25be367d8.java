/*
 * @test
 * @summary Test javax.crypto.Mac update(byte) method behavior
 * @run main javax.crypto.MacWitupTest
 */

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.Provider;

public class MacWitupTest {
    public static void main(String[] args) throws Exception {
        testUpdateWithoutInitThrows();
        testUpdateAfterInitSucceeds();
    }

    private static void testUpdateWithoutInitThrows() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        boolean thrown = false;
        try {
            mac.update((byte)0x01);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when update called before init");
        }
    }

    private static void testUpdateAfterInitSucceeds() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = new byte[] { 0x01, 0x02, 0x03, 0x04 };
        javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(key);
        // Should not throw
        mac.update((byte)0x01);
        // Also test multiple updates
        mac.update((byte)0x02);
        mac.update((byte)0x03);
    }
}
