/*
 * @test
 * @summary Checks PBEKeySpec(char[], byte[], int) argument validation and the empty-password/empty-salt exception path
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] emptySalt = new byte[0];
        char[] password = "password".toCharArray();

        assertNPE(password, null, 1000);
        assertIAE(password, emptySalt, 1000);
        assertIAE(password, salt, 0);
        assertIAE(password, salt, -1);
        assertIAE(null, emptySalt, 1000);
        assertIAE(new char[0], emptySalt, 1000);

        assertNoException(password, salt, 1);
        assertNoException(password, salt, 1000);
        assertNoException(null, salt, 1000);
        assertNoException(new char[0], salt, 1000);
    }

    static void assertNPE(char[] pw, byte[] salt, int iterations) {
        try {
            new PBEKeySpec(pw, salt, iterations);
        } catch (NullPointerException e) {
            return;
        } catch (RuntimeException e) {
            throw new RuntimeException("Unexpected exception: " + e, e);
        }
        throw new RuntimeException("Expected NullPointerException");
    }

    static void assertIAE(char[] pw, byte[] salt, int iterations) {
        try {
            new PBEKeySpec(pw, salt, iterations);
        } catch (IllegalArgumentException e) {
            return;
        } catch (RuntimeException e) {
            throw new RuntimeException("Unexpected exception: " + e, e);
        }
        throw new RuntimeException("Expected IllegalArgumentException");
    }

    static void assertNoException(char[] pw, byte[] salt, int iterations) {
        new PBEKeySpec(pw, salt, iterations);
    }
}
