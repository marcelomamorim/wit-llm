/*
 * @test
 * @summary Test java.lang.Integer.parseInt(String) for valid and invalid inputs
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        testValidParse();
        testNullInput();
        testEmptyString();
        testInvalidNumber();
    }

    private static void testValidParse() {
        // Normal decimal parsing
        int val = Integer.parseInt("12345");
        if (val != 12345) {
            throw new Error("Expected 12345 but got " + val);
        }
        // Negative number
        val = Integer.parseInt("-9876");
        if (val != -9876) {
            throw new Error("Expected -9876 but got " + val);
        }
        // Zero
        val = Integer.parseInt("0");
        if (val != 0) {
            throw new Error("Expected 0 but got " + val);
        }
    }

    private static void testNullInput() {
        try {
            Integer.parseInt(null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testEmptyString() {
        try {
            Integer.parseInt("");
            throw new Error("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testInvalidNumber() {
        try {
            Integer.parseInt("abc123");
            throw new Error("Expected NumberFormatException for invalid number string");
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
