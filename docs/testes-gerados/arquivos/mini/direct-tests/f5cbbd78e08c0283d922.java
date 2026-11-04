/*
 * @test
 * @summary Test java.lang.Byte.valueOf(String s) method
 * @run main java.lang.ByteWitupTest
 */

public class ByteWitupTest {
    public static void main(String[] args) {
        testValidDecimalString();
        testValidNegativeDecimalString();
        testInvalidString();
        testEmptyString();
        testNullString();
    }

    private static void testValidDecimalString() {
        Byte b = Byte.valueOf("127");
        if (b.byteValue() != 127) {
            throw new RuntimeException("Expected 127 but got " + b);
        }
    }

    private static void testValidNegativeDecimalString() {
        Byte b = Byte.valueOf("-128");
        if (b.byteValue() != -128) {
            throw new RuntimeException("Expected -128 but got " + b);
        }
    }

    private static void testInvalidString() {
        try {
            Byte.valueOf("128"); // Out of byte range
            throw new RuntimeException("Expected NumberFormatException for value 128");
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            Byte.valueOf("abc"); // Not a number
            throw new RuntimeException("Expected NumberFormatException for value 'abc'");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testEmptyString() {
        try {
            Byte.valueOf("");
            throw new RuntimeException("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testNullString() {
        try {
            Byte.valueOf((String) null);
            throw new RuntimeException("Expected NullPointerException for null string");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
