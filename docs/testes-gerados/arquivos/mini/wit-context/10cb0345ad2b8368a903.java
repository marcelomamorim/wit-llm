/*
 * @test
 * @summary Test java.util.Date(String s) constructor for valid and invalid inputs
 * @run main DateWitupTest
 */

import java.util.Date;

public class DateWitupTest {
    public static void main(String[] args) {
        testValidDateString();
        testInvalidDateString();
    }

    private static void testValidDateString() {
        // Test with a valid date string format recognized by Date.parse
        // Example: "Jan 1, 1970" is a valid date string
        try {
            Date d = new Date("Jan 1, 1970");
            if (d == null) {
                throw new RuntimeException("Date constructor returned null for valid input");
            }
            // The time should be 0 or close to 0 (epoch start)
            long time = d.getTime();
            if (time < 0 || time > 1000 * 60 * 60 * 24) {
                throw new RuntimeException("Date time out of expected range for valid input: " + time);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Date constructor threw IllegalArgumentException for valid input", e);
        }
    }

    private static void testInvalidDateString() {
        // Test with an invalid date string that should cause IllegalArgumentException
        // According to WIT, the constructor throws IllegalArgumentException on parse failure
        String invalidDate = "invalid-date-string-xyz";
        try {
            new Date(invalidDate);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for invalid input");
        } catch (IllegalArgumentException expected) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type thrown for invalid input", e);
        }
    }
}
