/*
 * @test
 * @summary Test for javax.crypto.spec.SecretKeySpec constructor behavior
 * @run main SecretKeySpecWitupTest
 */

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class SecretKeySpecWitupTest {

    public static void main(String[] args) {
        testNullArguments();
        testEmptyKey();
        testValidConstruction();
    }

    private static void testNullArguments() {
        boolean exceptionThrown = false;
        try {
            new SecretKeySpec(null, "AES");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null key");
        }

        exceptionThrown = false;
        try {
            new SecretKeySpec(new byte[]{1, 2, 3}, null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null algorithm");
        }
    }

    private static void testEmptyKey() {
        boolean exceptionThrown = false;
        try {
            new SecretKeySpec(new byte[0], "AES");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for empty key");
        }
    }

    private static void testValidConstruction() {
        byte[] keyBytes = {1, 2, 3, 4, 5, 6, 7, 8};
        String algorithm = "AES";
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, algorithm);
        if (keySpec == null) {
            throw new AssertionError("SecretKeySpec instance is null");
        }
        if (!Arrays.equals(keyBytes, getKeyBytes(keySpec))) {
            throw new AssertionError("Key bytes do not match");
        }
        if (!algorithm.equals(getAlgorithmName(keySpec))) {
            throw new AssertionError("Algorithm name does not match");
        }
        // Verify that the key array is a clone, not the same reference
        keyBytes[0] = 99;
        if (getKeyBytes(keySpec)[0] == 99) {
            throw new AssertionError("Key array was not cloned properly");
        }
    }

    private static byte[] getKeyBytes(SecretKeySpec spec) {
        try {
            java.lang.reflect.Field keyField = SecretKeySpec.class.getDeclaredField("key");
            keyField.setAccessible(true);
            return (byte[]) keyField.get(spec);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed to access key field", e);
        }
    }

    private static String getAlgorithmName(SecretKeySpec spec) {
        try {
            java.lang.reflect.Field algField = SecretKeySpec.class.getDeclaredField("algorithm");
            algField.setAccessible(true);
            return (String) algField.get(spec);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed to access algorithm field", e);
        }
    }
}
