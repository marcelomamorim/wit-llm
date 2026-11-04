/*
 * @test
 * @summary Test PBEKeySpec constructor with char[] password
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;
import java.util.Arrays;

public class PBEKeySpecWitupTest {
    public static void main(String[] args) {
        testNullPassword();
        testEmptyPassword();
        testNonEmptyPassword();
    }

    private static void testNullPassword() {
        PBEKeySpec spec = new PBEKeySpec(null);
        char[] pwd = spec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("Password should not be null when constructed with null");
        }
        if (pwd.length != 0) {
            throw new RuntimeException("Password length should be 0 when constructed with null");
        }
    }

    private static void testEmptyPassword() {
        char[] empty = new char[0];
        PBEKeySpec spec = new PBEKeySpec(empty);
        char[] pwd = spec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("Password should not be null when constructed with empty array");
        }
        if (pwd.length != 0) {
            throw new RuntimeException("Password length should be 0 when constructed with empty array");
        }
    }

    private static void testNonEmptyPassword() {
        char[] original = new char[] {'a', 'b', 'c'};
        PBEKeySpec spec = new PBEKeySpec(original);
        char[] pwd = spec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("Password should not be null when constructed with non-empty array");
        }
        if (pwd.length != original.length) {
            throw new RuntimeException("Password length mismatch");
        }
        if (!Arrays.equals(pwd, original)) {
            throw new RuntimeException("Password content mismatch");
        }
        // Modify original array and check that spec password is not affected (clone test)
        original[0] = 'z';
        char[] pwdAfter = spec.getPassword();
        if (pwdAfter[0] == 'z') {
            throw new RuntimeException("Password array was not cloned properly");
        }
    }
}
