/*
 * @test
 * @summary Test sun.util.BuddhistCalendar.getDisplayNames method behavior
 * @run main sun.util.BuddhistCalendarWitupTest
 */

import java.util.Locale;
import java.util.Map;
import sun.util.BuddhistCalendar;

public class BuddhistCalendarWitupTest {
    public static void main(String[] args) throws Exception {
        BuddhistCalendar cal = new BuddhistCalendar();

        // Test with field != ERA (e.g. YEAR)
        Map<String, Integer> namesYear = cal.getDisplayNames(java.util.Calendar.YEAR, java.util.Calendar.ALL_STYLES, Locale.US);
        if (namesYear == null) {
            throw new RuntimeException("getDisplayNames returned null for YEAR field");
        }

        // Test with field == ERA
        Map<String, Integer> namesEra = cal.getDisplayNames(java.util.Calendar.ERA, java.util.Calendar.ALL_STYLES, Locale.US);
        if (namesEra == null) {
            throw new RuntimeException("getDisplayNames returned null for ERA field");
        }

        // Check that the ERA map contains at least one entry (expected for buddhist calendar)
        if (namesEra.isEmpty()) {
            throw new RuntimeException("getDisplayNames returned empty map for ERA field");
        }

        // Test with different style (SHORT) and Locale.FRANCE
        Map<String, Integer> namesEraShort = cal.getDisplayNames(java.util.Calendar.ERA, java.util.Calendar.SHORT, Locale.FRANCE);
        if (namesEraShort == null || namesEraShort.isEmpty()) {
            throw new RuntimeException("getDisplayNames returned null or empty map for ERA field with SHORT style and Locale.FRANCE");
        }

        System.out.println("All tests passed.");
    }
}
