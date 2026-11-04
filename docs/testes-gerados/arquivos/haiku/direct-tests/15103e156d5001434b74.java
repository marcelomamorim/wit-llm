/*
 * @test
 * @summary Witup-generated checks for Calendar.getDisplayNames(int, int, Locale)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testMonthAllStylesContainsJanuary();
        testFieldOutsideMaskReturnsNull();
        testDayOfWeekAllStylesReturnsNonEmptyMap();
        testResultIsIndependentlyUsable();
        System.out.println("PASSED");
    }

    private static void testMonthAllStylesContainsJanuary() {
        Calendar cal = Calendar.getInstance(Locale.US);
        Map<String, Integer> names = cal.getDisplayNames(Calendar.MONTH, Calendar.ALL_STYLES, Locale.US);
        check(names != null, "MONTH names must not be null for ALL_STYLES");
        Integer january = names.get("January");
        check(january != null, "'January' must be present in MONTH names");
        check(january.intValue() == Calendar.JANUARY,
              "'January' must map to Calendar.JANUARY, got " + january);
    }

    private static void testFieldOutsideMaskReturnsNull() {
        Calendar cal = Calendar.getInstance(Locale.US);
        Map<String, Integer> names = cal.getDisplayNames(Calendar.HOUR, Calendar.ALL_STYLES, Locale.US);
        check(names == null, "HOUR is not a supported display-name field and must yield null");
    }

    private static void testDayOfWeekAllStylesReturnsNonEmptyMap() {
        Calendar cal = Calendar.getInstance(Locale.US);
        Map<String, Integer> names = cal.getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.ALL_STYLES, Locale.US);
        check(names != null, "DAY_OF_WEEK names must not be null for ALL_STYLES");
        check(!names.isEmpty(), "DAY_OF_WEEK names must not be empty");
        Integer sunday = names.get("Sunday");
        check(sunday != null && sunday.intValue() == Calendar.SUNDAY,
              "'Sunday' must map to Calendar.SUNDAY");
    }

    private static void testResultIsIndependentlyUsable() {
        Calendar cal = Calendar.getInstance(Locale.US);
        Map<String, Integer> first = cal.getDisplayNames(Calendar.AM_PM, Calendar.ALL_STYLES, Locale.US);
        check(first != null, "AM_PM names must not be null");
        Integer am = first.get("AM");
        check(am != null && am.intValue() == Calendar.AM, "'AM' must map to Calendar.AM");
        Map<String, Integer> second = cal.getDisplayNames(Calendar.AM_PM, Calendar.ALL_STYLES, Locale.US);
        check(second != null && second.equals(first),
              "repeated calls with same arguments must return equal maps");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
