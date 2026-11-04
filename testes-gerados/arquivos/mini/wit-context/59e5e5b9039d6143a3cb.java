/*
 * @test
 * @summary Test constructor Short(String s) for valid and invalid inputs
 * @run main ShortWitupTest
 */

public class ShortWitupTest {
    public static void main(String[] args) {
        testValidString();
        testNullString();
        testEmptyString();
        testInvalidString();
    }

    private static void testValidString() {
        // Valid decimal string
        Short s = new Short("12345");
        if (s.shortValue() != 12345) {
            throw new Error("Failed to parse valid string '12345'");
        }
        // Valid negative number
        Short s2 = new Short("-32768");
        if (s2.shortValue() != -32768) {
            throw new Error("Failed to parse valid string '-32768'");
        }
        // Valid positive number with plus sign
        Short s3 = new Short("+32767");
        if (s3.shortValue() != 32767) {
            throw new Error("Failed to parse valid string '+32767'");
        }
    }

    private static void testNullString() {
        try {
            new Short(null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testEmptyString() {
        try {
            new Short("");
            throw new Error("Expected NumberFormatException for empty string input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testInvalidString() {
        try {
            new Short("12a34");
            throw new Error("Expected NumberFormatException for invalid string input");
        } catch (NumberFormatException e) {
            // Expected
        }
        try {
            new Short("32768"); // Out of short range
            throw new Error("Expected NumberFormatException for out of range input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
