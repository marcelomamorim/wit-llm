/*
 * @test
 * @summary Test javax.crypto.Mac.doFinal() behavior including IllegalStateException when not initialized
 * @run main MacWitupTest
 */

import javax.crypto.Mac;
import javax.crypto.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;

public class MacWitupTest {
    public static void main(String[] args) throws Exception {
        testDoFinalThrowsIfNotInitialized();
        testDoFinalReturnsMacBytesWhenInitialized();
    }

    private static void testDoFinalThrowsIfNotInitialized() throws Exception {
        // Obtain a Mac instance but do NOT initialize it with a key
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.doFinal();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    private static void testDoFinalReturnsMacBytesWhenInitialized() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        // Initialize with a dummy key
        byte[] keyBytes = new byte[16];
        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) i;
        }
        javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(key);

        // Update with some data
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        mac.update(data);

        byte[] result = mac.doFinal();
        if (result == null || result.length == 0) {
            throw new RuntimeException("doFinal() returned null or empty array");
        }

        // Calling doFinal again should produce a valid MAC (reset after doFinal)
        byte[] result2 = mac.doFinal();
        if (result2 == null || result2.length == 0) {
            throw new RuntimeException("Second doFinal() returned null or empty array");
        }
    }
}
