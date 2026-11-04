/*
 * @test
 * @summary Test DHKeyPairGenerator.initialize(int, SecureRandom) method
 * @run main com.sun.crypto.provider.DHKeyPairGeneratorWitupTest
 */

import java.security.InvalidParameterException;
import java.security.SecureRandom;
import com.sun.crypto.provider.DHKeyPairGenerator;

public class DHKeyPairGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeySizes();
        testInvalidKeySizes();
        testUnsupportedKeySizeGeneration();
    }

    private static void testValidKeySizes() {
        DHKeyPairGenerator gen = new DHKeyPairGenerator();
        SecureRandom random = new SecureRandom();

        // Test valid key size 512 (minimum allowed)
        gen.initialize(512, random);

        // Test valid key size 1024 (supported upper bound for generation)
        gen.initialize(1024, random);

        // Test valid key size 2048 (should use cached params, no exception)
        gen.initialize(2048, random);

        // Test valid key size 8192 (maximum allowed)
        gen.initialize(8192, random);
    }

    private static void testInvalidKeySizes() {
        DHKeyPairGenerator gen = new DHKeyPairGenerator();

        // Key size less than 512
        try {
            gen.initialize(256, null);
            throw new RuntimeException("Expected InvalidParameterException for keysize 256");
        } catch (InvalidParameterException e) {
            // expected
        }

        // Key size greater than 8192
        try {
            gen.initialize(8256, null);
            throw new RuntimeException("Expected InvalidParameterException for keysize 8256");
        } catch (InvalidParameterException e) {
            // expected
        }

        // Key size not multiple of 64
        try {
            gen.initialize(513, null);
            throw new RuntimeException("Expected InvalidParameterException for keysize 513");
        } catch (InvalidParameterException e) {
            // expected
        }
    }

    private static void testUnsupportedKeySizeGeneration() {
        DHKeyPairGenerator gen = new DHKeyPairGenerator();

        // Key size > 1024 and no cached params (simulate by using a key size that likely has no cached params)
        // According to code, if params == null and keysize > 1024, throws InvalidParameterException
        // 1536 is multiple of 64 and between 512 and 8192, so valid key size
        try {
            gen.initialize(1536, null);
            throw new RuntimeException("Expected InvalidParameterException for unsupported 1536-bit DH parameter generation");
        } catch (InvalidParameterException e) {
            // expected
        }
    }
}
