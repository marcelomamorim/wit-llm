/*
 * @test
 * @summary Witup-generated checks for the PBEKeySpec(char[] password) constructor: null and empty passwords map to an empty array, and the input array is cloned.
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        testNullPasswordYieldsEmptyArray();
        testEmptyPasswordYieldsEmptyArray();
        testPasswordIsClonedFromInput();
        testNonEmptyPasswordIsPreserved();
    }

    static void testNullPasswordYieldsEmptyArray() {
        PBEKeySpec spec = new PBEKeySpec(null);
        char[] pwd = spec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("getPassword() returned null for null input");
        }
        if (pwd.length != 0) {
            throw new RuntimeException("Expected empty password for null input, length=" + pwd.length);
        }
    }

    static void testEmptyPasswordYieldsEmptyArray() {
        PBEKeySpec spec = new PBEKeySpec(new char[0]);
        char[] pwd = spec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("getPassword() returned null for empty input");
        }
        if (pwd.length != 0) {
            throw new RuntimeException("Expected empty password for empty input, length=" + pwd.length);
        }
    }

    static void testPasswordIsClonedFromInput() {
        char[] input = {'a', 'b', 'c'};
        PBEKeySpec spec = new PBEKeySpec(input);
        input[0] = 'z';
        char[] pwd = spec.getPassword();
        if (pwd[0] != 'a') {
            throw new RuntimeException("Password was not cloned from input array");
        }
        if (pwd.length != 3 || pwd[1] != 'b' || pwd[2] != 'c') {
            throw new RuntimeException("Unexpected password contents after cloning");
        }
    }

    static void testNonEmptyPasswordIsPreserved() {
        char[] input = {'p', 'w', 'd', '1'};
        PBEKeySpec spec = new PBEKeySpec(input);
        char[] pwd = spec.getPassword();
        if (pwd.length != input.length) {
            throw new RuntimeException("Length mismatch: " + pwd.length);
        }
        for (int i = 0; i < input.length; i++) {
            if (pwd[i] != input[i]) {
                throw new RuntimeException("Mismatch at index " + i);
            }
        }
    }
}
