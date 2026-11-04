/*
 * @test
 * @summary Test java.lang.Integer.valueOf(String) for valid and invalid inputs
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        testValidString();
        testNullString();
        testInvalidString();
    }

    private static void testValidString() {
        // Test with a valid decimal string
        Integer i = Integer.valueOf("123");
        if (i.intValue() != 123) {
            throw new Error("Expected 123 but got " + i);
        }

        // Test with zero
        i = Integer.valueOf("0");
        if (i.intValue() != 0) {
            throw new Error("Expected 0 but got " + i);
        }

        // Test with negative number
        i = Integer.valueOf("-456");
        if (i.intValue() != -456) {
            throw new Error("Expected -456 but got " + i);
        }
    }

    private static void testNullString() {
        // According to WIT expath, passing null should throw NumberFormatException
        try {
            Integer.valueOf((String) null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected exception
        } catch (NullPointerException e) {
            // Defensive: if implementation throws NPE instead, accept it as well
        }
    }

    private static void testInvalidString() {
        // Test with a non-numeric string to provoke NumberFormatException
        try {
            Integer.valueOf("abc123");
            throw new Error("Expected NumberFormatException for invalid input");
        } catch (NumberFormatException e) {
            // Expected exception
        }

        // Test with empty string
        try {
            Integer.valueOf("");
            throw new Error("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
