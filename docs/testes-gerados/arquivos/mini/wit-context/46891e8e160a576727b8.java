/*
 * @test
 * @summary Test java.util.Calendar.Builder.setWeekDefinition(int, int) for valid and invalid inputs
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testValidWeekDefinition();
        testInvalidWeekDefinition();
    }

    private static void testValidWeekDefinition() {
        Calendar.Builder builder = new Calendar.Builder();
        // Valid values: firstDayOfWeek and minimalDaysInFirstWeek in [1..7]
        for (int firstDay = 1; firstDay <= 7; firstDay++) {
            for (int minimalDays = 1; minimalDays <= 7; minimalDays++) {
                Calendar.Builder result = builder.setWeekDefinition(firstDay, minimalDays);
                if (result != builder) {
                    throw new AssertionError("setWeekDefinition should return this builder instance");
                }
            }
        }
    }

    private static void testInvalidWeekDefinition() {
        Calendar.Builder builder = new Calendar.Builder();
        int[] invalidValues = {0, -1, 8, 100};

        for (int invalidFirstDay : invalidValues) {
            try {
                builder.setWeekDefinition(invalidFirstDay, 1);
                throw new AssertionError("Expected IllegalArgumentException for firstDayOfWeek=" + invalidFirstDay);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }

        for (int invalidMinimalDays : invalidValues) {
            try {
                builder.setWeekDefinition(1, invalidMinimalDays);
                throw new AssertionError("Expected IllegalArgumentException for minimalDaysInFirstWeek=" + invalidMinimalDays);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }
}
