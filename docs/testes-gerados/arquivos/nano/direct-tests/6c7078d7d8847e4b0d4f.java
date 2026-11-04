/* @test    @summary Test constructor SecretKeySpec(byte[] key, int offset, int len, String algorithm) for valid and invalid inputs    @run main SecretKeySpecWitupTest */

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class SecretKeySpecWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullArguments();
        testEmptyKey();
        testInvalidOffsetAndLength();
        testNegativeLength();
    }

    static void testValidConstructor() {
        byte[] keyBytes = {1, 2, 3, 4, 5};
        String algorithm = "AES";
        SecretKeySpec spec = new SecretKeySpec(keyBytes, 1, 3, algorithm);
        assert Arrays.equals(spec.key, new byte[]{2, 3, 4}) : "Key bytes do not match";
        assert spec.algorithm.equals(algorithm) : "Algorithm does not match";
    }

    static void testNullArguments() {
        byte[] keyBytes = {1, 2, 3};
        try {
            new SecretKeySpec(null, 0, 1, "AES");
            assert false : "Expected IllegalArgumentException for null key";
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            new SecretKeySpec(keyBytes, 0, 1, null);
            assert false : "Expected IllegalArgumentException for null algorithm";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testEmptyKey() {
        byte[] emptyKey = {};
        try {
            new SecretKeySpec(emptyKey, "AES");
            assert false : "Expected IllegalArgumentException for empty key";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidOffsetAndLength() {
        byte[] keyBytes = {1, 2, 3, 4};
        try {
            new SecretKeySpec(keyBytes, 2, 3, "AES");
            assert false : "Expected IllegalArgumentException for invalid offset/length";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNegativeLength() {
        byte[] keyBytes = {1, 2, 3};
        try {
            new SecretKeySpec(keyBytes, 0, -1, "AES");
            assert false : "Expected ArrayIndexOutOfBoundsException for negative length";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
