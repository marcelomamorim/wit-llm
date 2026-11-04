/*
 * @test
 * @summary Test DHKeyPairGenerator constructor and default initialization
 * @run main DHKeyPairGeneratorWitupTest
 */

import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidParameterException;
import com.sun.crypto.provider.DHKeyPairGenerator;

public class DHKeyPairGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        // Test default constructor creates a usable KeyPairGenerator
        DHKeyPairGenerator gen = new DHKeyPairGenerator();
        // Generate a key pair to verify initialization
        KeyPair kp = gen.generateKeyPair();
        if (kp == null || kp.getPrivate() == null || kp.getPublic() == null) {
            throw new RuntimeException("Generated KeyPair or keys are null");
        }

        // Test that default key size is DEF_DH_KEY_SIZE (should be 1024 or 2048 typically)
        // Since DEF_DH_KEY_SIZE is not accessible, test that generateKeyPair works without exception

        // Test that initialize rejects invalid key sizes
        try {
            gen.initialize(511, null); // less than 512
            throw new RuntimeException("Expected InvalidParameterException for keysize 511");
        } catch (InvalidParameterException expected) {
        }

        try {
            gen.initialize(8193, null); // greater than 8192
            throw new RuntimeException("Expected InvalidParameterException for keysize 8193");
        } catch (InvalidParameterException expected) {
        }

        try {
            gen.initialize(513, null); // not multiple of 64
            throw new RuntimeException("Expected InvalidParameterException for keysize 513");
        } catch (InvalidParameterException expected) {
        }

        // Test that initialize throws for keysize > 1024 if no cached params
        // We cannot directly test no cached params, but 8192 is > 1024 and should throw
        try {
            gen.initialize(8192, null);
            throw new RuntimeException("Expected InvalidParameterException for unsupported 8192-bit DH parameter generation");
        } catch (InvalidParameterException expected) {
        }

        System.out.println("Test passed");
    }
}
