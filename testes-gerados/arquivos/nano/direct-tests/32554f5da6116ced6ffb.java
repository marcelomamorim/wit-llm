/* @test    @summary Test constructor of SecretKeySpec with valid inputs    @run main SecretKeySpecWitupTest */

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class SecretKeySpecWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullKeyThrows();
        testNullAlgorithmThrows();
        testEmptyKeyThrows();
    }

    static void testValidConstructor() {
        byte[] keyBytes = {1, 2, 3, 4, 5};
        String algorithm = "AES";
        SecretKeySpec spec = new SecretKeySpec(keyBytes, algorithm);
        assert spec != null : "Spec should not be null";
        assert Arrays.equals(keyBytes, spec.key) : "Key bytes should match";
        assert spec.algorithm.equals(algorithm) : "Algorithm should match";
        // Verify that the key array is a clone
        keyBytes[0] = 99;
        assert !Arrays.equals(spec.key, keyBytes) : "Spec key should be a clone, not affected by external modification";
    }

    static void testNullKeyThrows() {
        try {
            new SecretKeySpec(null, "AES");
            assert false : "Expected IllegalArgumentException for null key";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Missing argument") : "Exception message should be 'Missing argument'";
        }
    }

    static void testNullAlgorithmThrows() {
        try {
            new SecretKeySpec(new byte[]{1, 2, 3}, null);
            assert false : "Expected IllegalArgumentException for null algorithm";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Missing argument") : "Exception message should be 'Missing argument'";
        }
    }

    static void testEmptyKeyThrows() {
        try {
            new SecretKeySpec(new byte[]{}, "AES");
            assert false : "Expected IllegalArgumentException for empty key";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Empty key") : "Exception message should be 'Empty key'";
        }
    }
}
