/*
 * @test
 * @summary Test for Calendar.setWeekDefinition to verify handling of invalid parameters.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetWeekDefinitionInvalidFirstDay();
        testSetWeekDefinitionInvalidMinimalDays();
        testSetWeekDefinitionValidParameters();
    }

    private static void testSetWeekDefinitionInvalidFirstDay() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setWeekDefinition(-1, 3);
            System.out.println("FAIL: Expected IllegalArgumentException for firstDayOfWeek = -1");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for firstDayOfWeek = -1");
        }
        try {
            builder.setWeekDefinition(8, 3);
            System.out.println("FAIL: Expected IllegalArgumentException for firstDayOfWeek = 8");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for firstDayOfWeek = 8");
        }
    }

    private static void testSetWeekDefinitionInvalidMinimalDays() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setWeekDefinition(1, -1);
            System.out.println("FAIL: Expected IllegalArgumentException for minimalDaysInFirstWeek = -1");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for minimalDaysInFirstWeek = -1");
        }
        try {
            builder.setWeekDefinition(1, 8);
            System.out.println("FAIL: Expected IllegalArgumentException for minimalDaysInFirstWeek = 8");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for minimalDaysInFirstWeek = 8");
        }
    }

    private static void testSetWeekDefinitionValidParameters() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            Calendar.Builder result = builder.setWeekDefinition(1, 4);
            if (result == builder) {
                System.out.println("PASS: setWeekDefinition returned the same builder instance");
            } else {
                System.out.println("FAIL: setWeekDefinition did not return the same builder instance");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid parameters: " + e);
        }
    }
}
