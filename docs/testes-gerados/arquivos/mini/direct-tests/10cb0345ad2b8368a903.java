/*
 * @test
 * @summary Test java.util.Date(String) constructor with valid and invalid inputs
 * @run main DateWitupTest
 */

public class DateWitupTest {
    public static void main(String[] args) {
        testValidDateString();
        testInvalidDateString();
    }

    private static void testValidDateString() {
        // Test with a valid date string format recognized by Date.parse
        // Example: "Jan 1 1970"
        java.util.Date d = new java.util.Date("Jan 1 1970");
        if (d.getTime() != 0L) {
            throw new RuntimeException("Expected epoch time 0 for 'Jan 1 1970', got: " + d.getTime());
        }
    }

    private static void testInvalidDateString() {
        // Test with an invalid date string, expecting IllegalArgumentException
        // The Date(String) constructor calls parse(s) which throws IllegalArgumentException on failure
        boolean thrown = false;
        try {
            new java.util.Date("invalid-date-string");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for invalid date string");
        }
    }
}
