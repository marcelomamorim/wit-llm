/*
 * @test
 * @summary Test javax.crypto.spec.PBEKeySpec constructor with password, salt and iterationCount
 * @run main PBEKeySpecWitupTest
 */

public class PBEKeySpecWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullSaltThrowsNPE();
        testEmptySaltThrowsIAE();
        testZeroIterationCountThrowsIAE();
        testNegativeIterationCountThrowsIAE();
    }

    private static void testValidConstructor() {
        char[] password = {'p', 'a', 's', 's'};
        byte[] salt = {1, 2, 3};
        int iterationCount = 1000;
        javax.crypto.spec.PBEKeySpec spec = new javax.crypto.spec.PBEKeySpec(password, salt, iterationCount);
        // Check that password is cloned (not same reference)
        if (spec.getPassword() == password) {
            throw new RuntimeException("Password array was not cloned");
        }
        // Check that salt is cloned (not same reference)
        byte[] specSalt = spec.getSalt();
        if (specSalt == salt) {
            throw new RuntimeException("Salt array was not cloned");
        }
        // Check iterationCount is set correctly
        if (spec.getIterationCount() != iterationCount) {
            throw new RuntimeException("Iteration count not set correctly");
        }
    }

    private static void testNullSaltThrowsNPE() {
        char[] password = {'p'};
        byte[] salt = null;
        int iterationCount = 1;
        try {
            new javax.crypto.spec.PBEKeySpec(password, salt, iterationCount);
            throw new RuntimeException("Expected NullPointerException for null salt");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testEmptySaltThrowsIAE() {
        char[] password = {'p'};
        byte[] salt = new byte[0];
        int iterationCount = 1;
        try {
            new javax.crypto.spec.PBEKeySpec(password, salt, iterationCount);
            throw new RuntimeException("Expected IllegalArgumentException for empty salt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testZeroIterationCountThrowsIAE() {
        char[] password = {'p'};
        byte[] salt = {1};
        int iterationCount = 0;
        try {
            new javax.crypto.spec.PBEKeySpec(password, salt, iterationCount);
            throw new RuntimeException("Expected IllegalArgumentException for zero iterationCount");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testNegativeIterationCountThrowsIAE() {
        char[] password = {'p'};
        byte[] salt = {1};
        int iterationCount = -1;
        try {
            new javax.crypto.spec.PBEKeySpec(password, salt, iterationCount);
            throw new RuntimeException("Expected IllegalArgumentException for negative iterationCount");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
