/*
 * @test
 * @summary Tests for java.util.Date constructor with String parameter
 * @run main DateWitupTest
 */

import java.util.Date;

public class DateWitupTest {

    public static void main(String[] args) {
        testValidDateString();
        testInvalidDateString();
    }

    private static void testValidDateString() {
        String validDateStr = "Jan 1, 2000";
        try {
            Date date = new Date(validDateStr);
            System.out.println("Valid date string parsed: " + date);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Parsing valid date string should not throw", e);
        }
    }

    private static void testInvalidDateString() {
        String invalidDateStr = "Invalid Date";
        try {
            Date date = new Date(invalidDateStr);
            throw new AssertionError("Parsing invalid date string should throw IllegalArgumentException, but got: " + date);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for invalid input.");
        }
    }
}
