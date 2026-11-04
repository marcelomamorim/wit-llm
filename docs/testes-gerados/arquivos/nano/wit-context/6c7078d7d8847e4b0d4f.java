/*
 * @test
 * @summary Tests for javax.crypto.spec.SecretKeySpec constructor with byte array, offset, length, and algorithm.
 * @run main SecretKeySpecWitupTest
 */

import javax.crypto.spec.SecretKeySpec;

public class SecretKeySpecWitupTest {

    public static void main(String[] args) {
        testNullArguments();
        testEmptyKey();
        testInvalidOffsetLength();
        testNegativeLength();
    }

    static void testNullArguments() {
        byte[] validKey = {1, 2, 3, 4};
        String validAlg = "AES";
        try {
            new SecretKeySpec(null, 0, 2, validAlg);
            throw new AssertionError("Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            new SecretKeySpec(validKey, 0, 2, null);
            throw new AssertionError("Expected IllegalArgumentException for null algorithm");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testEmptyKey() {
        byte[] emptyKey = {};
        String validAlg = "AES";
        try {
            new SecretKeySpec(emptyKey, 0, 0, validAlg);
            throw new AssertionError("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidOffsetLength() {
        byte[] key = {1, 2, 3, 4};
        String validAlg = "AES";
        // offset + length exceeds key length
        try {
            new SecretKeySpec(key, 3, 2, validAlg);
            throw new AssertionError("Expected IllegalArgumentException for invalid offset/length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNegativeLength() {
        byte[] key = {1, 2, 3, 4};
        String validAlg = "AES";
        try {
            new SecretKeySpec(key, 0, -1, validAlg);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
