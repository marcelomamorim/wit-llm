/*
 * @test
 * @summary Test java.util.Calendar.Builder.setWeekDefinition method behavior
 * @run main java.util.CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testValidWeekDefinition();
        testInvalidFirstDayOfWeek();
        testInvalidMinimalDaysInFirstWeek();
    }

    private static void testValidWeekDefinition() {
        Calendar.Builder builder = new Calendar.Builder();
        // Use valid values for firstDayOfWeek and minimalDaysInFirstWeek
        int validFirstDay = Calendar.MONDAY; // 2
        int validMinimalDays = 4;
        Calendar.Builder result = builder.setWeekDefinition(validFirstDay, validMinimalDays);
        if (result != builder) {
            throw new AssertionError("setWeekDefinition should return the same Builder instance");
        }
    }

    private static void testInvalidFirstDayOfWeek() {
        Calendar.Builder builder = new Calendar.Builder();
        int invalidFirstDay = 0; // invalid, valid range is 1..7
        int validMinimalDays = 1;
        try {
            builder.setWeekDefinition(invalidFirstDay, validMinimalDays);
            throw new AssertionError("Expected IllegalArgumentException for invalid firstDayOfWeek");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testInvalidMinimalDaysInFirstWeek() {
        Calendar.Builder builder = new Calendar.Builder();
        int validFirstDay = Calendar.SUNDAY; // 1
        int invalidMinimalDays = 0; // invalid, valid range is 1..7
        try {
            builder.setWeekDefinition(validFirstDay, invalidMinimalDays);
            throw new AssertionError("Expected IllegalArgumentException for invalid minimalDaysInFirstWeek");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
