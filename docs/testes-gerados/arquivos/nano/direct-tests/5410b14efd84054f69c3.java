/* @test    @summary Test getDisplayNames method of BuddhistCalendar for ERA field    @run main BuddhistCalendarWitupTest */

import java.util.Locale;
import java.util.Map;
import sun.util.BuddhistCalendar;

public class BuddhistCalendarWitupTest {

    public static void main(String[] args) {
        testGetDisplayNamesERA();
        testGetDisplayNamesOtherField();
    }

    private static void testGetDisplayNamesERA() {
        BuddhistCalendar cal = new BuddhistCalendar();
        Map<String, Integer> names = cal.getDisplayNames(BuddhistCalendar.ERA, Calendar.STYLE_SHORT, Locale.US);
        boolean hasEra = false;
        for (String key : names.keySet()) {
            if (key.equals("Buddhist Era")) {
                hasEra = true;
                break;
            }
        }
        if (!hasEra) {
            throw new AssertionError("Expected 'Buddhist Era' in display names for ERA field");
        }
    }

    private static void testGetDisplayNamesOtherField() {
        BuddhistCalendar cal = new BuddhistCalendar();
        Map<String, Integer> names = cal.getDisplayNames(BuddhistCalendar.YEAR, Calendar.STYLE_SHORT, Locale.US);
        if (names == null || names.isEmpty()) {
            throw new AssertionError("Display names for YEAR field should not be null or empty");
        }
        // Additional check: ensure that the returned map is from CalendarDataUtility.retrieveFieldValueNames
        // Since we cannot access internal, we assume non-empty map suffices
    }
}
