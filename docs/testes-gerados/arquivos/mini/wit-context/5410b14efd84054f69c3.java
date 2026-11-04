/*
 * @test
 * @summary Test sun.util.BuddhistCalendar.getDisplayNames(int, int, Locale) for valid and invalid inputs
 * @run main sun.util.BuddhistCalendarWitupTest
 */

import java.util.Locale;
import java.util.Map;
import sun.util.BuddhistCalendar;

public class BuddhistCalendarWitupTest {
    public static void main(String[] args) {
        testGetDisplayNamesValid();
        testGetDisplayNamesInvalid();
    }

    private static void testGetDisplayNamesValid() {
        BuddhistCalendar cal = new BuddhistCalendar();
        Locale locale = Locale.US;

        // Valid field: ERA (0), style: ALL_STYLES (-1)
        Map<String, Integer> names = cal.getDisplayNames(BuddhistCalendar.ERA, BuddhistCalendar.ALL_STYLES, locale);
        if (names == null || names.isEmpty()) {
            throw new RuntimeException("Expected non-empty map for ERA field");
        }

        // Valid field: MONTH (2), style: SHORT (2)
        names = cal.getDisplayNames(BuddhistCalendar.MONTH, 2, locale);
        if (names == null || names.isEmpty()) {
            throw new RuntimeException("Expected non-empty map for MONTH field");
        }
    }

    private static void testGetDisplayNamesInvalid() {
        BuddhistCalendar cal = new BuddhistCalendar();
        Locale locale = Locale.US;

        // Invalid field: negative and not ERA (0)
        int invalidField = -1;
        Map<String, Integer> result = null;
        try {
            result = cal.getDisplayNames(invalidField, BuddhistCalendar.ALL_STYLES, locale);
            // According to current code, super.getDisplayNames may throw IllegalArgumentException for invalid field
            // But if it returns null, test that result is null
            if (result != null) {
                throw new RuntimeException("Expected null or exception for invalid field");
            }
        } catch (IllegalArgumentException e) {
            // Expected path
        }

        // Invalid style: style out of range (e.g. 100) with valid field (MONTH)
        int invalidStyle = 100;
        try {
            result = cal.getDisplayNames(BuddhistCalendar.MONTH, invalidStyle, locale);
            if (result != null) {
                throw new RuntimeException("Expected null or exception for invalid style");
            }
        } catch (IllegalArgumentException e) {
            // Expected path
        }

        // Invalid field: field >= fields.length (simulate with large number)
        int largeField = 1000;
        try {
            result = cal.getDisplayNames(largeField, BuddhistCalendar.ALL_STYLES, locale);
            if (result != null) {
                throw new RuntimeException("Expected null or exception for large invalid field");
            }
        } catch (IllegalArgumentException e) {
            // Expected path
        }
    }
}
