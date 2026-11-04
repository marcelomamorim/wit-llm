/*
 * @test
 * @summary Test javax.crypto.Mac update(byte[]) method behavior
 * @run main MacWitupTest
 */

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.Provider;
import java.security.Provider.Service;
import java.util.Iterator;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithNullInput();
        testUpdateWithoutInitThrows();
        testUpdateWithValidInput();
    }

    // Test that update(null) does not throw and does not call engineUpdate
    private static void testUpdateWithNullInput() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new javax.crypto.spec.SecretKeySpec(new byte[16], "HmacSHA256"));
        // Should not throw
        mac.update((byte[]) null);
    }

    // Test that update throws IllegalStateException if Mac not initialized
    private static void testUpdateWithoutInitThrows() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.update(new byte[] {1, 2, 3});
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    // Test that update with valid input does not throw and processes input
    private static void testUpdateWithValidInput() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new javax.crypto.spec.SecretKeySpec(new byte[16], "HmacSHA256"));
        byte[] input = {10, 20, 30, 40};
        mac.update(input);
        // Call doFinal to verify no exception and output length > 0
        byte[] result = mac.doFinal();
        if (result == null || result.length == 0) {
            throw new RuntimeException("doFinal returned empty result");
        }
    }
}
