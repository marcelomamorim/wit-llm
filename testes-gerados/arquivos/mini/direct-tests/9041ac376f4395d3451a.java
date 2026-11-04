/*
 * @test
 * @summary Test javax.crypto.spec.PBEKeySpec constructor with password, salt and iterationCount
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullPassword();
        testEmptyPassword();
        testNullSalt();
        testEmptySalt();
        testInvalidIterationCount();
    }

    private static void testValidConstructor() {
        char[] password = {'p', 'a', 's', 's'};
        byte[] salt = {1, 2, 3, 4};
        int iterationCount = 1000;
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount);
        // Check that password is cloned (not same reference)
        if (keySpec == null) {
            throw new RuntimeException("PBEKeySpec constructor returned null");
        }
    }

    private static void testNullPassword() {
        byte[] salt = {1, 2, 3, 4};
        int iterationCount = 1000;
        PBEKeySpec keySpec = new PBEKeySpec(null, salt, iterationCount);
        if (keySpec == null) {
            throw new RuntimeException("PBEKeySpec constructor with null password returned null");
        }
    }

    private static void testEmptyPassword() {
        char[] password = new char[0];
        byte[] salt = {1, 2, 3, 4};
        int iterationCount = 1000;
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount);
        if (keySpec == null) {
            throw new RuntimeException("PBEKeySpec constructor with empty password returned null");
        }
    }

    private static void testNullSalt() {
        char[] password = {'p', 'a', 's', 's'};
        int iterationCount = 1000;
        try {
            new PBEKeySpec(password, null, iterationCount);
            throw new RuntimeException("Expected NullPointerException for null salt");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testEmptySalt() {
        char[] password = {'p', 'a', 's', 's'};
        byte[] salt = new byte[0];
        int iterationCount = 1000;
        try {
            new PBEKeySpec(password, salt, iterationCount);
            throw new RuntimeException("Expected IllegalArgumentException for empty salt");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidIterationCount() {
        char[] password = {'p', 'a', 's', 's'};
        byte[] salt = {1, 2, 3, 4};
        int iterationCount = 0;
        try {
            new PBEKeySpec(password, salt, iterationCount);
            throw new RuntimeException("Expected IllegalArgumentException for iterationCount <= 0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
