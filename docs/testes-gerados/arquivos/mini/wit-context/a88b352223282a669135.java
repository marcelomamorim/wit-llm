/*
 * @test
 * @summary Test Integer(String s) constructor for valid and invalid inputs
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        testValidString();
        testNullString();
        testEmptyString();
        testInvalidString();
    }

    private static void testValidString() {
        // Valid decimal string
        Integer i = new Integer("123");
        if (i.intValue() != 123) {
            throw new Error("Expected 123 but got " + i.intValue());
        }

        // Valid negative decimal string
        Integer j = new Integer("-456");
        if (j.intValue() != -456) {
            throw new Error("Expected -456 but got " + j.intValue());
        }
    }

    private static void testNullString() {
        try {
            new Integer((String) null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testEmptyString() {
        try {
            new Integer("");
            throw new Error("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testInvalidString() {
        try {
            new Integer("abc123");
            throw new Error("Expected NumberFormatException for invalid string");
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
