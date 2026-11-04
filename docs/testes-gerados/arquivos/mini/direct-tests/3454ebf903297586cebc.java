/*
 * @test
 * @summary Test java.lang.Byte.parseByte(String) method
 * @run main ByteWitupTest
 */

public class ByteWitupTest {
    public static void main(String[] args) {
        testValidDecimalStrings();
        testInvalidStrings();
        testNullString();
    }

    private static void testValidDecimalStrings() {
        // Test zero
        byte zero = java.lang.Byte.parseByte("0");
        if (zero != 0) throw new RuntimeException("Expected 0 but got " + zero);

        // Test positive number
        byte pos = java.lang.Byte.parseByte("127");
        if (pos != 127) throw new RuntimeException("Expected 127 but got " + pos);

        // Test negative number
        byte neg = java.lang.Byte.parseByte("-128");
        if (neg != -128) throw new RuntimeException("Expected -128 but got " + neg);

        // Test small positive number
        byte smallPos = java.lang.Byte.parseByte("42");
        if (smallPos != 42) throw new RuntimeException("Expected 42 but got " + smallPos);

        // Test small negative number
        byte smallNeg = java.lang.Byte.parseByte("-42");
        if (smallNeg != -42) throw new RuntimeException("Expected -42 but got " + smallNeg);
    }

    private static void testInvalidStrings() {
        // Test empty string
        try {
            java.lang.Byte.parseByte("");
            throw new RuntimeException("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }

        // Test string with non-digit characters
        try {
            java.lang.Byte.parseByte("12a");
            throw new RuntimeException("Expected NumberFormatException for non-digit characters");
        } catch (NumberFormatException e) {
            // expected
        }

        // Test string representing number out of byte range (too large)
        try {
            java.lang.Byte.parseByte("128");
            throw new RuntimeException("Expected NumberFormatException for value > 127");
        } catch (NumberFormatException e) {
            // expected
        }

        // Test string representing number out of byte range (too small)
        try {
            java.lang.Byte.parseByte("-129");
            throw new RuntimeException("Expected NumberFormatException for value < -128");
        } catch (NumberFormatException e) {
            // expected
        }

        // Test string with spaces
        try {
            java.lang.Byte.parseByte(" 1");
            throw new RuntimeException("Expected NumberFormatException for string with leading space");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            java.lang.Byte.parseByte("1 ");
            throw new RuntimeException("Expected NumberFormatException for string with trailing space");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testNullString() {
        try {
            java.lang.Byte.parseByte(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
