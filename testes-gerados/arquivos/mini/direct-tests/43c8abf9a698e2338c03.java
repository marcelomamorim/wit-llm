/*
 * @test
 * @summary Test java.lang.Short.valueOf(String s) method
 * @run main ShortWitupTest
 */

public class ShortWitupTest {
    public static void main(String[] args) {
        testValidDecimalString();
        testValidNegativeDecimalString();
        testInvalidString();
        testNullString();
        testEmptyString();
    }

    private static void testValidDecimalString() {
        Short s = Short.valueOf("12345");
        if (s.shortValue() != 12345) {
            throw new Error("Expected 12345 but got " + s);
        }
    }

    private static void testValidNegativeDecimalString() {
        Short s = Short.valueOf("-32768");
        if (s.shortValue() != -32768) {
            throw new Error("Expected -32768 but got " + s);
        }
    }

    private static void testInvalidString() {
        try {
            Short.valueOf("abc");
            throw new Error("Expected NumberFormatException for input 'abc'");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testNullString() {
        try {
            Short.valueOf(null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testEmptyString() {
        try {
            Short.valueOf("");
            throw new Error("Expected NumberFormatException for empty string input");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
