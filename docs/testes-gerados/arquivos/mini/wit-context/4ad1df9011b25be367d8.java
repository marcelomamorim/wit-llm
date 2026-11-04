/*
 * @test
 * @summary Test javax.crypto.Mac.update(byte) for IllegalStateException when not initialized
 * @run main MacWitupTest
 */

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;

public class MacWitupTest {
    public static void main(String[] args) throws Exception {
        testUpdateThrowsIfNotInitialized();
    }

    private static void testUpdateThrowsIfNotInitialized() throws Exception {
        // Obtain a Mac instance for a known algorithm
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Required algorithm HmacSHA256 not available", e);
        }

        // We deliberately do NOT initialize the Mac with a key
        // Calling update(byte) should throw IllegalStateException
        boolean threw = false;
        try {
            mac.update((byte)0x00);
        } catch (IllegalStateException ise) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling update(byte) on uninitialized Mac");
        }
    }
}
