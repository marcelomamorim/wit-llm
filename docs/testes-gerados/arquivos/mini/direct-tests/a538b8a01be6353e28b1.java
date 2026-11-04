/*
 * @test
 * @summary Test sun.security.provider.DSAKeyPairGenerator.initialize(int, SecureRandom)
 * @run main sun.security.provider.DSAKeyPairGeneratorWitupTest
 */

import java.security.SecureRandom;
import sun.security.provider.DSAKeyPairGenerator;

public class DSAKeyPairGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        DSAKeyPairGenerator gen = new DSAKeyPairGenerator(1024);

        // Test initialize with valid key size and null SecureRandom
        gen.initialize(1024, null);

        // Test initialize with valid key size and non-null SecureRandom
        SecureRandom sr = new SecureRandom();
        gen.initialize(1024, sr);

        // Test initialize with minimum valid key size
        gen.initialize(512, sr);

        // Test initialize with maximum valid key size
        gen.initialize(1024, sr);

        // Test initialize with valid larger key sizes
        gen.initialize(2048, sr);
        gen.initialize(3072, sr);

        // Test initialize with invalid key size - expect InvalidParameterException
        try {
            gen.initialize(500, sr);
            throw new RuntimeException("Expected InvalidParameterException for key size 500");
        } catch (java.security.InvalidParameterException e) {
            // expected
        }

        try {
            gen.initialize(1025, sr);
            throw new RuntimeException("Expected InvalidParameterException for key size 1025");
        } catch (java.security.InvalidParameterException e) {
            // expected
        }

        try {
            gen.initialize(0, sr);
            throw new RuntimeException("Expected InvalidParameterException for key size 0");
        } catch (java.security.InvalidParameterException e) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
