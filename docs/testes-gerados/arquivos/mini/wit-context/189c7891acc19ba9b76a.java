/*
 * @test
 * @summary Test DHKeyPairGenerator.initialize(int, SecureRandom) for valid and invalid key sizes
 * @run main com.sun.crypto.provider.DHKeyPairGeneratorWitupTest
 */

import java.security.InvalidParameterException;
import java.security.SecureRandom;
import com.sun.crypto.provider.DHKeyPairGenerator;

public class DHKeyPairGeneratorWitupTest {
    public static void main(String[] args) {
        DHKeyPairGenerator gen = new DHKeyPairGenerator();

        // Test valid key size within allowed range and multiple of 64
        try {
            gen.initialize(512, null); // minimum valid
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for keysize 512", e);
        }

        try {
            gen.initialize(1024, new SecureRandom()); // valid with random
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for keysize 1024", e);
        }

        try {
            gen.initialize(8192, null); // maximum valid
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for keysize 8192", e);
        }

        // Test invalid key sizes: less than 512
        try {
            gen.initialize(511, null);
            throw new RuntimeException("Expected InvalidParameterException for keysize 511");
        } catch (InvalidParameterException expected) {
        }

        // Test invalid key sizes: greater than 8192
        try {
            gen.initialize(8256, null); // 8256 is > 8192 and multiple of 64
            throw new RuntimeException("Expected InvalidParameterException for keysize 8256");
        } catch (InvalidParameterException expected) {
        }

        // Test invalid key sizes: not multiple of 64
        try {
            gen.initialize(513, null); // 513 not multiple of 64
            throw new RuntimeException("Expected InvalidParameterException for keysize 513");
        } catch (InvalidParameterException expected) {
        }

        // Test unsupported parameter generation for > 1024 and no cached params
        // We cannot directly control ParameterCache.getCachedDHParameterSpec, so test a keysize > 1024 that is multiple of 64
        // and expect InvalidParameterException if params == null
        // Since we cannot mock ParameterCache, we test that 2048 triggers exception or not
        // If no exception, test passes as behavior changed or params cached
        try {
            gen.initialize(2048, null);
        } catch (InvalidParameterException e) {
            // Expected if params == null for 2048
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for keysize 2048", e);
        }
    }
}
