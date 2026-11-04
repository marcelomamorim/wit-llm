/*
 * @test
 * @summary Verifies observable behavior of PBEKeySpec.getPassword(): empty password for null input, returned copy, and input cloning.
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullPasswordYieldsEmptyArray();
        testGetPasswordReturnsEqualContent();
        testGetPasswordReturnsIndependentCopy();
        testConstructorClonesInputPassword();
        testGetPasswordWithSaltConstructor();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullPasswordYieldsEmptyArray() {
        PBEKeySpec spec = new PBEKeySpec(null);
        char[] pwd = spec.getPassword();
        check(pwd != null, "getPassword() returned null for null input");
        check(pwd.length == 0, "expected empty password for null input, got length " + pwd.length);
    }

    static void testGetPasswordReturnsEqualContent() {
        char[] input = {'a', 'b', 'c', '1'};
        PBEKeySpec spec = new PBEKeySpec(input);
        char[] pwd = spec.getPassword();
        check(pwd.length == 4, "unexpected length: " + pwd.length);
        check(pwd[0] == 'a' && pwd[1] == 'b' && pwd[2] == 'c' && pwd[3] == '1',
              "password content mismatch");
    }

    static void testGetPasswordReturnsIndependentCopy() {
        PBEKeySpec spec = new PBEKeySpec(new char[] {'x', 'y'});
        char[] first = spec.getPassword();
        first[0] = 'Z';
        char[] second = spec.getPassword();
        check(second[0] == 'x', "mutating returned array affected stored password");
        check(first != second, "getPassword() returned the same array instance");
    }

    static void testConstructorClonesInputPassword() {
        char[] input = {'p', 'w'};
        PBEKeySpec spec = new PBEKeySpec(input);
        input[0] = 'Q';
        char[] pwd = spec.getPassword();
        check(pwd[0] == 'p', "constructor did not clone input password");
    }

    static void testGetPasswordWithSaltConstructor() {
        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8};
        PBEKeySpec spec = new PBEKeySpec(new char[] {'s', 'e', 'c'}, salt, 1000, 128);
        char[] pwd = spec.getPassword();
        check(pwd.length == 3 && pwd[0] == 's' && pwd[1] == 'e' && pwd[2] == 'c',
              "password mismatch with salt constructor");
    }
}
