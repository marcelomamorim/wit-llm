/*
 * @test
 * @summary Test java.lang.Byte.parseByte(String) for valid and invalid inputs
 * @run main ByteWitupTest
 */

public class ByteWitupTest {
    public static void main(String[] args) {
        testValidParse();
        testNullInput();
        testEmptyString();
        testInvalidString();
        System.out.println("All tests passed.");
    }

    private static void testValidParse() {
        // Valid decimal strings
        byte b1 = java.lang.Byte.parseByte("0");
        if (b1 != 0) throw new RuntimeException("Expected 0 but got " + b1);

        byte b2 = java.lang.Byte.parseByte("127");
        if (b2 != 127) throw new RuntimeException("Expected 127 but got " + b2);

        byte b3 = java.lang.Byte.parseByte("-128");
        if (b3 != -128) throw new RuntimeException("Expected -128 but got " + b3);
    }

    private static void testNullInput() {
        try {
            java.lang.Byte.parseByte(null);
            throw new RuntimeException("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testEmptyString() {
        try {
            java.lang.Byte.parseByte("");
            throw new RuntimeException("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testInvalidString() {
        try {
            java.lang.Byte.parseByte("abc");
            throw new RuntimeException("Expected NumberFormatException for non-numeric string");
        } catch (NumberFormatException e) {
            // Expected
        }

        try {
            java.lang.Byte.parseByte("128"); // Out of byte range
            throw new RuntimeException("Expected NumberFormatException for out of range value");
        } catch (NumberFormatException e) {
            // Expected
        }

        try {
            java.lang.Byte.parseByte("12.3"); // Invalid format
            throw new RuntimeException("Expected NumberFormatException for invalid format");
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
