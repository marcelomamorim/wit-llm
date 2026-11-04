/*
 * @test
 * @summary Test javax.crypto.spec.PBEKeySpec constructor with char[] password
 * @run main PBEKeySpecWitupTest
 */

public class PBEKeySpecWitupTest {
    public static void main(String[] args) {
        testNullPassword();
        testEmptyPassword();
        testValidPasswordClone();
    }

    private static void testNullPassword() {
        javax.crypto.spec.PBEKeySpec keySpec = new javax.crypto.spec.PBEKeySpec(null);
        char[] pwd = keySpec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("Password should not be null when constructed with null input");
        }
        if (pwd.length != 0) {
            throw new RuntimeException("Password length should be 0 when constructed with null input");
        }
    }

    private static void testEmptyPassword() {
        char[] empty = new char[0];
        javax.crypto.spec.PBEKeySpec keySpec = new javax.crypto.spec.PBEKeySpec(empty);
        char[] pwd = keySpec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("Password should not be null when constructed with empty array");
        }
        if (pwd.length != 0) {
            throw new RuntimeException("Password length should be 0 when constructed with empty array");
        }
    }

    private static void testValidPasswordClone() {
        char[] original = new char[] {'a', 'b', 'c'};
        javax.crypto.spec.PBEKeySpec keySpec = new javax.crypto.spec.PBEKeySpec(original);
        char[] pwd = keySpec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("Password should not be null when constructed with valid array");
        }
        if (pwd.length != original.length) {
            throw new RuntimeException("Password length mismatch");
        }
        for (int i = 0; i < pwd.length; i++) {
            if (pwd[i] != original[i]) {
                throw new RuntimeException("Password content mismatch at index " + i);
            }
        }
        // Modify original and check that keySpec password is unaffected (clone test)
        original[0] = 'z';
        char[] pwdAfterChange = keySpec.getPassword();
        if (pwdAfterChange[0] == 'z') {
            throw new RuntimeException("Password array was not cloned; changes to original affected internal state");
        }
    }
}
