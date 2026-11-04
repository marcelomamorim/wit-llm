/*
 * @test    @summary Test Calendar.setWeekDefinition method for valid and invalid parameters
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testValidParameters();
        testInvalidFirstDayOfWeek();
        testInvalidMinimalDaysInFirstWeek();
    }

    static void testValidParameters() {
        Calendar cal = Calendar.getInstance();
        try {
            Calendar.Builder builder = new Calendar.Builder();
            builder.setWeekDefinition(Calendar.MONDAY, 4).build();
            Calendar cal2 = new Calendar.Builder().setWeekDefinition(Calendar.MONDAY, 4).build();
            cal2.setWeekDefinition(Calendar.TUESDAY, 3);
            System.out.println("Valid parameters test passed.");
        } catch (Exception e) {
            System.err.println("Valid parameters test failed: " + e);
        }
    }

    static void testInvalidFirstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setWeekDefinition(8, 4); // Invalid firstDayOfWeek
            System.err.println("Invalid firstDayOfWeek test failed: exception expected.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid firstDayOfWeek test passed.");
        }
    }

    static void testInvalidMinimalDaysInFirstWeek() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setWeekDefinition(Calendar.MONDAY, 0); // Invalid minimalDaysInFirstWeek
            System.err.println("Invalid minimalDaysInFirstWeek test failed: exception expected.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid minimalDaysInFirstWeek test passed.");
        }
    }
}
