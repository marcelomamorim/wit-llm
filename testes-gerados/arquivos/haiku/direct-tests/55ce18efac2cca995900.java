/*
 * @test
 * @summary Verify PBEKeySpec(char[]) clones the password and handles null and empty input
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        testPasswordIsCloned();
        testNullPasswordYieldsEmptyArray();
        testEmptyPasswordYieldsEmptyArray();
    }

    static void testPasswordIsCloned() {
        char[] pw = {'a', 'b', 'c'};
        PBEKeySpec spec = new PBEKeySpec(pw);
        pw[0] = 'z';
        char[] stored = spec.getPassword();
        check(stored.length == 3, "length must be 3");
        check(stored[0] == 'a' && stored[1] == 'b' && stored[2] == 'c',
              "stored password must not reflect later mutation of input");
    }

    static void testNullPasswordYieldsEmptyArray() {
        PBEKeySpec spec = new PBEKeySpec(null);
        char[] stored = spec.getPassword();
        check(stored != null, "password must not be null");
        check(stored.length == 0, "null password must yield empty array");
    }

    static void testEmptyPasswordYieldsEmptyArray() {
        PBEKeySpec spec = new PBEKeySpec(new char[0]);
        char[] stored = spec.getPassword();
        check(stored != null, "password must not be null");
        check(stored.length == 0, "empty password must yield empty array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
