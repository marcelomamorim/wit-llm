/* @test    @summary Test PBEKeySpec constructor with salt and iterationCount    @run main PBEKeySpecWitupTest */

import javax.crypto.spec.PBEKeySpec;
import java.util.Arrays;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidSaltAndIteration();
        testConstructorWithNullSalt();
        testConstructorWithEmptySalt();
        testConstructorWithInvalidIteration();
        testConstructorWithNullPassword();
        testConstructorWithEmptyPassword();
    }

    static void testConstructorWithValidSaltAndIteration() {
        char[] password = {'p', 'a', 's', 's'};
        byte[] salt = {1, 2, 3, 4};
        int iterationCount = 1000;
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterationCount);
        assert Arrays.equals(spec.password, password) : "Password not stored correctly";
        assert Arrays.equals(spec.salt, salt) : "Salt not stored correctly";
        assert spec.iterationCount == iterationCount : "Iteration count not stored correctly";
        // Overwrite password array to test security
        Arrays.fill(password, 'x');
        assert Arrays.equals(spec.password, new char[]{'p', 'a', 's', 's'}) : "Password array was modified externally";
    }

    static void testConstructorWithNullSalt() {
        char[] password = {'p', 'a', 's', 's'};
        byte[] salt = null;
        int iterationCount = 1000;
        try {
            new PBEKeySpec(password, salt, iterationCount);
            assert false : "Expected NullPointerException for null salt";
        } catch (NullPointerException e) {
            assert e.getMessage().contains("must be non-null") : "Unexpected exception message";
        }
    }

    static void testConstructorWithEmptySalt() {
        char[] password = {'p', 'a', 's', 's'};
        byte[] salt = {};
        int iterationCount = 1000;
        try {
            new PBEKeySpec(password, salt, iterationCount);
            assert false : "Expected IllegalArgumentException for empty salt";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("must not be empty") : "Unexpected exception message";
        }
    }

    static void testConstructorWithInvalidIteration() {
        char[] password = {'p', 'a', 's', 's'};
        byte[] salt = {1, 2, 3};
        int iterationCount = 0;
        try {
            new PBEKeySpec(password, salt, iterationCount);
            assert false : "Expected IllegalArgumentException for invalid iterationCount";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("invalid iterationCount") : "Unexpected exception message";
        }
    }

    static void testConstructorWithNullPassword() {
        byte[] salt = {1, 2, 3};
        int iterationCount = 1000;
        PBEKeySpec spec = new PBEKeySpec(null, salt, iterationCount);
        assert spec.password.length == 0 : "Password should be empty array when null is passed";
    }

    static void testConstructorWithEmptyPassword() {
        char[] password = {};
        byte[] salt = {1, 2, 3};
        int iterationCount = 1000;
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterationCount);
        assert spec.password.length == 0 : "Password should be empty array when empty array is passed";
    }
}
