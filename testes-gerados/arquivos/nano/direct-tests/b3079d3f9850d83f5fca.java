/* @test    @summary Test Integer.valueOf(String s, int radix) for various inputs    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {

    public static void main(String[] args) {
        testValidRadixAndString();
        testInvalidRadix();
        testInvalidNumberFormat();
        testEmptyString();
        testNullString();
    }

    static void testValidRadixAndString() {
        String s = "10";
        int radix = 10;
        Integer result = Integer.valueOf(s, radix);
        if (result == null || result.intValue() != 10) {
            throw new AssertionError("Failed: valid radix and string") ;
        }
    }

    static void testInvalidRadix() {
        String s = "10";
        int invalidRadix = 1; // radix below Character.MIN_RADIX
        try {
            Integer.valueOf(s, invalidRadix);
            throw new AssertionError("Expected NumberFormatException for invalid radix") ;
        } catch (NumberFormatException e) {
            // expected
        }
        invalidRadix = 37; // radix above Character.MAX_RADIX
        try {
            Integer.valueOf(s, invalidRadix);
            throw new AssertionError("Expected NumberFormatException for invalid radix") ;
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testInvalidNumberFormat() {
        String s = "abc";
        int radix = 10;
        try {
            Integer.valueOf(s, radix);
            throw new AssertionError("Expected NumberFormatException for invalid number") ;
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testEmptyString() {
        String s = "";
        int radix = 10;
        try {
            Integer.valueOf(s, radix);
            throw new AssertionError("Expected NumberFormatException for empty string") ;
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testNullString() {
        String s = null;
        int radix = 10;
        try {
            Integer.valueOf(s, radix);
            throw new AssertionError("Expected NullPointerException for null string") ;
        } catch (NullPointerException e) {
            // expected
        }
    }
}
