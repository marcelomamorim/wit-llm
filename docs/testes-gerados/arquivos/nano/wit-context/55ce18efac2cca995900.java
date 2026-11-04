/* @test    @summary Test for PBEKeySpec constructor with char[] password    @run main PBEKeySpecWitupTest */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullPassword();
        testConstructorWithEmptyPassword();
        testConstructorWithValidPassword();
    }

    private static void testConstructorWithNullPassword() {
        try {
            PBEKeySpec spec = new PBEKeySpec((char[]) null);
            if (spec == null) {
                throw new AssertionError("Constructor with null password returned null");
            }
            if (spec.password == null || spec.password.length != 0) {
                throw new AssertionError("Password array not initialized correctly for null input");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null password: " + e);
        }
    }

    private static void testConstructorWithEmptyPassword() {
        try {
            char[] empty = new char[0];
            PBEKeySpec spec = new PBEKeySpec(empty);
            if (spec == null) {
                throw new AssertionError("Constructor with empty password returned null");
            }
            if (spec.password != empty && spec.password.length != 0) {
                throw new AssertionError("Password array not initialized correctly for empty input");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty password: " + e);
        }
    }

    private static void testConstructorWithValidPassword() {
        try {
            char[] pwd = {'a', 'b', 'c'};
            PBEKeySpec spec = new PBEKeySpec(pwd);
            if (spec == null) {
                throw new AssertionError("Constructor with valid password returned null");
            }
            if (spec.password == pwd) {
                throw new AssertionError("Password array was not cloned, mutable external array detected");
            }
            if (spec.password.length != pwd.length) {
                throw new AssertionError("Cloned password array length mismatch");
            }
            for (int i = 0; i < pwd.length; i++) {
                if (spec.password[i] != pwd[i]) {
                    throw new AssertionError("Cloned password array content mismatch");
                }
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid password: " + e);
        }
    }
}
