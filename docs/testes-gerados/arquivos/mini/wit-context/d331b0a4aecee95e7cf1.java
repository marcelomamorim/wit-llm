/*
 * @test
 * @summary Test java.util.Calendar.Builder.setCalendarType(String) for valid and invalid inputs
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testValidTypes();
        testInvalidTypes();
        testTypeOverride();
    }

    private static void testValidTypes() {
        Calendar.Builder builder = new Calendar.Builder();
        // "gregorian" is converted internally to "gregory"
        builder.setCalendarType("gregorian");
        // Setting the same type again should not throw
        builder.setCalendarType("gregorian");
        // Test with "gregory" explicitly
        Calendar.Builder builder2 = new Calendar.Builder();
        builder2.setCalendarType("gregory");
        builder2.setCalendarType("gregory");
        // Test with "iso8601" which is allowed
        Calendar.Builder builder3 = new Calendar.Builder();
        builder3.setCalendarType("iso8601");
        builder3.setCalendarType("iso8601");
    }

    private static void testInvalidTypes() {
        Calendar.Builder builder = new Calendar.Builder();
        // Test with an invalid type that is not in available calendar types and not "iso8601"
        String invalidType = "invalidType";
        try {
            builder.setCalendarType(invalidType);
            throw new RuntimeException("Expected IllegalArgumentException for invalid type");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        // Test with "gregorian" replaced by invalid type to trigger exception
        try {
            builder.setCalendarType("gregorian"); // converts to "gregory" internally
            builder.setCalendarType(invalidType);
            throw new RuntimeException("Expected IllegalArgumentException for invalid type after valid");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testTypeOverride() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("gregorian"); // sets to "gregory"
        try {
            // Attempt to override with a different valid type
            builder.setCalendarType("iso8601");
            throw new RuntimeException("Expected IllegalStateException for calendar type override");
        } catch (IllegalStateException e) {
            // Expected
        }
        // Also test override with different string that is not "gregorian" but valid
        Calendar.Builder builder2 = new Calendar.Builder();
        builder2.setCalendarType("iso8601");
        try {
            builder2.setCalendarType("gregorian");
            throw new RuntimeException("Expected IllegalStateException for calendar type override");
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}
