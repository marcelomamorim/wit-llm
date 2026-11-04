/*
 * @test
 * @summary Test Byte(String s) constructor for valid and invalid inputs
 * @run main ByteWitupTest
 */

public class ByteWitupTest {
    public static void main(String[] args) {
        testValidString();
        testNullString();
        testEmptyString();
        testInvalidString();
    }

    private static void testValidString() {
        // Valid decimal string
        Byte b = new Byte("123");
        if (b.byteValue() != 123) {
            throw new Error("Expected 123 but got " + b.byteValue());
        }
        // Valid negative decimal string
        Byte b2 = new Byte("-128");
        if (b2.byteValue() != -128) {
            throw new Error("Expected -128 but got " + b2.byteValue());
        }
    }

    private static void testNullString() {
        try {
            new Byte(null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testEmptyString() {
        try {
            new Byte("");
            throw new Error("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testInvalidString() {
        try {
            new Byte("abc");
            throw new Error("Expected NumberFormatException for non-numeric string");
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
