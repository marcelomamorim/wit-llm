/* @test    @summary Test for DHKeyPairGenerator constructor behavior and exception paths    @run main DHKeyPairGeneratorWitupTest */

import com.sun.crypto.provider.DHKeyPairGenerator;
import java.security.InvalidParameterException;

public class DHKeyPairGeneratorWitupTest {

    public static void main(String[] args) {
        testConstructorWithInvalidKeySize();
        testConstructorWithValidKeySize();
    }

    private static void testConstructorWithInvalidKeySize() {
        // Temporarily override DEF_DH_KEY_SIZE to an invalid value for testing
        int originalSize = sun.security.util.SecurityProviderConstants.DEF_DH_KEY_SIZE;
        try {
            // Set to a value less than 512 to trigger exception
            setDefDhKeySize(256);
            boolean exceptionThrown = false;
            try {
                new DHKeyPairGenerator();
            } catch (InvalidParameterException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected InvalidParameterException for key size 256");
            }
        } finally {
            setDefDhKeySize(originalSize);
        }
    }

    private static void testConstructorWithValidKeySize() {
        // Set to a valid size, e.g., 1024
        int validSize = 1024;
        setDefDhKeySize(validSize);
        try {
            DHKeyPairGenerator generator = new DHKeyPairGenerator();
            if (generator == null) {
                throw new AssertionError("DHKeyPairGenerator instance should not be null");
            }
        } finally {
            // Reset to original if needed
        }
    }

    private static void setDefDhKeySize(int size) {
        try {
            java.lang.reflect.Field field = sun.security.util.SecurityProviderConstants.class.getDeclaredField("DEF_DH_KEY_SIZE");
            field.setAccessible(true);
            field.setInt(null, size);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set DEF_DH_KEY_SIZE", e);
        }
    }
}
