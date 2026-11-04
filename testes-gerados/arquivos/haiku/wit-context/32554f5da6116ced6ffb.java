/*
 * @test
 * @summary Witup-generated tests for SecretKeySpec(byte[], String) constructor
 * @run main SecretKeySpecWitupTest
 */

import javax.crypto.spec.SecretKeySpec;

public class SecretKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullKeyRejected();
        testNullAlgorithmRejected();
        testEmptyKeyRejected();
        testDefensiveCopy();
        testValidKeyPreserved();
    }

    static void expectIllegalArgument(byte[] key, String algorithm, String label) {
        try {
            new SecretKeySpec(key, algorithm);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException(label + ": expected IllegalArgumentException");
    }

    static void testNullKeyRejected() {
        expectIllegalArgument(null, "AES", "null key");
    }

    static void testNullAlgorithmRejected() {
        expectIllegalArgument(new byte[] {1, 2, 3, 4}, null, "null algorithm");
    }

    static void testEmptyKeyRejected() {
        expectIllegalArgument(new byte[0], "AES", "empty key");
    }

    static void testDefensiveCopy() {
        byte[] input = {1, 2, 3, 4};
        SecretKeySpec spec = new SecretKeySpec(input, "AES");
        input[0] = 9;
        byte[] encoded = spec.getEncoded();
        if (encoded.length != 4) {
            throw new RuntimeException("unexpected key length: " + encoded.length);
        }
        if (encoded[0] != 1) {
            throw new RuntimeException("key was not copied defensively");
        }
    }

    static void testValidKeyPreserved() {
        byte[] input = {10, 20, 30, 40, 50, 60, 70, 80};
        SecretKeySpec spec = new SecretKeySpec(input, "AES");
        if (!"AES".equals(spec.getAlgorithm())) {
            throw new RuntimeException("unexpected algorithm: " + spec.getAlgorithm());
        }
        byte[] encoded = spec.getEncoded();
        if (encoded.length != input.length) {
            throw new RuntimeException("unexpected encoded length: " + encoded.length);
        }
        for (int i = 0; i < input.length; i++) {
            if (encoded[i] != input[i]) {
                throw new RuntimeException("mismatch at index " + i);
            }
        }
    }
}
