/*
 * @test
 * @summary Witup-generated checks for the UnicodeSet(String pattern) constructor
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidPatternDoesNotThrow() {
        UnicodeSet set = new UnicodeSet("[a-z]");
        check(set != null, "constructor returned null for [a-z]");
    }

    static void testEmptyPatternClassDoesNotThrow() {
        UnicodeSet set = new UnicodeSet("[]");
        check(set != null, "constructor returned null for []");
    }

    static void testUnterminatedPatternThrows() {
        boolean thrown = false;
        try {
            new UnicodeSet("[a-");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for unterminated pattern");
    }

    public static void main(String[] args) {
        testValidPatternDoesNotThrow();
        testEmptyPatternClassDoesNotThrow();
        testUnterminatedPatternThrows();
    }
}
