/*
 * @test
 * @summary Test Long.parseLong(String) method basic and edge cases
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        testValidDecimalStrings();
        testInvalidStrings();
        testEdgeValues();
    }

    private static void testValidDecimalStrings() {
        // Simple positive number
        long val = java.lang.Long.parseLong("12345");
        if (val != 12345L) {
            throw new Error("Failed to parse positive number");
        }

        // Simple negative number
        val = java.lang.Long.parseLong("-98765");
        if (val != -98765L) {
            throw new Error("Failed to parse negative number");
        }

        // Zero
        val = java.lang.Long.parseLong("0");
        if (val != 0L) {
            throw new Error("Failed to parse zero");
        }

        // Leading plus sign
        val = java.lang.Long.parseLong("+42");
        if (val != 42L) {
            throw new Error("Failed to parse number with plus sign");
        }
    }

    private static void testInvalidStrings() {
        // Null string should throw NullPointerException
        try {
            java.lang.Long.parseLong(null);
            throw new Error("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }

        // Empty string should throw NumberFormatException
        try {
            java.lang.Long.parseLong("");
            throw new Error("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }

        // String with invalid characters
        try {
            java.lang.Long.parseLong("12a34");
            throw new Error("Expected NumberFormatException for invalid characters");
        } catch (NumberFormatException e) {
            // expected
        }

        // String with only sign
        try {
            java.lang.Long.parseLong("-");
            throw new Error("Expected NumberFormatException for only sign");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            java.lang.Long.parseLong("+");
            throw new Error("Expected NumberFormatException for only sign");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testEdgeValues() {
        // Long.MAX_VALUE
        long val = java.lang.Long.parseLong("9223372036854775807");
        if (val != java.lang.Long.MAX_VALUE) {
            throw new Error("Failed to parse Long.MAX_VALUE");
        }

        // Long.MIN_VALUE
        val = java.lang.Long.parseLong("-9223372036854775808");
        if (val != java.lang.Long.MIN_VALUE) {
            throw new Error("Failed to parse Long.MIN_VALUE");
        }

        // Overflow positive
        try {
            java.lang.Long.parseLong("9223372036854775808");
            throw new Error("Expected NumberFormatException for overflow positive");
        } catch (NumberFormatException e) {
            // expected
        }

        // Overflow negative
        try {
            java.lang.Long.parseLong("-9223372036854775809");
            throw new Error("Expected NumberFormatException for overflow negative");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
