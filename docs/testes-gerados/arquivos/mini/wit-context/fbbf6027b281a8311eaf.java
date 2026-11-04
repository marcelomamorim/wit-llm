/*
 * @test
 * @summary Test java.lang.Long.parseLong(String) for valid, invalid and null inputs
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        testValidDecimalString();
        testInvalidString();
        testNullString();
    }

    private static void testValidDecimalString() {
        // Valid decimal string should parse correctly
        String valid = "1234567890";
        long result = java.lang.Long.parseLong(valid);
        if (result != 1234567890L) {
            throw new RuntimeException("Failed to parse valid decimal string: " + valid);
        }
    }

    private static void testInvalidString() {
        // Invalid string should throw NumberFormatException
        String invalid = ""; // empty string triggers NumberFormatException
        try {
            java.lang.Long.parseLong(invalid);
            throw new RuntimeException("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }

        String invalid2 = "abc123"; // non-numeric string
        try {
            java.lang.Long.parseLong(invalid2);
            throw new RuntimeException("Expected NumberFormatException for non-numeric string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testNullString() {
        // Null string should throw NumberFormatException as per current implementation
        String nullStr = null;
        try {
            java.lang.Long.parseLong(nullStr);
            throw new RuntimeException("Expected NumberFormatException for null string");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
