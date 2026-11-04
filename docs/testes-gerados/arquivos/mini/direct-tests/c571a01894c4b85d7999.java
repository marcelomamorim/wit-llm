/*
 * @test
 * @summary Test Byte(String) constructor with valid and invalid inputs
 * @run main ByteWitupTest
 */

public class ByteWitupTest {
    public static void main(String[] args) {
        testValidString();
        testInvalidString();
    }

    private static void testValidString() {
        // Test with valid decimal string
        Byte b1 = new Byte("127");
        if (b1.byteValue() != 127) {
            throw new RuntimeException("Expected 127 but got " + b1.byteValue());
        }

        Byte b2 = new Byte("-128");
        if (b2.byteValue() != -128) {
            throw new RuntimeException("Expected -128 but got " + b2.byteValue());
        }

        Byte b3 = new Byte("0");
        if (b3.byteValue() != 0) {
            throw new RuntimeException("Expected 0 but got " + b3.byteValue());
        }
    }

    private static void testInvalidString() {
        // Test with invalid string that should throw NumberFormatException
        try {
            new Byte("128"); // out of byte range
            throw new RuntimeException("Expected NumberFormatException for value 128");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            new Byte("-129"); // out of byte range
            throw new RuntimeException("Expected NumberFormatException for value -129");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            new Byte("abc"); // not a number
            throw new RuntimeException("Expected NumberFormatException for value 'abc'");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            new Byte(""); // empty string
            throw new RuntimeException("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            new Byte(null); // null string
            throw new RuntimeException("Expected NullPointerException for null string");
        } catch (NullPointerException e) {
            // expected
        } catch (NumberFormatException e) {
            // NumberFormatException is also possible if null is handled differently
        }
    }
}
