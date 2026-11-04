/*
 * @test
 * @summary Test getDisplayNames method of Calendar class for various parameters
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testGetDisplayNamesWithValidParams();
        testGetDisplayNamesWithInvalidParams();
        testGetDisplayNamesWithFallback();
    }

    static void testGetDisplayNamesWithValidParams() {
        Calendar cal = Calendar.getInstance();
        Map<String, Integer> names = cal.getDisplayNames(Calendar.MONTH, Calendar.LONG, Locale.US);
        if (names == null || names.isEmpty()) {
            throw new AssertionError("Expected non-empty map for valid parameters");
        }
        boolean containsJanuary = false;
        for (String key : names.keySet()) {
            if (key.equals("January")) {
                containsJanuary = true;
                break;
            }
        }
        if (!containsJanuary) {
            throw new AssertionError("Map should contain "January"");
        }
    }

    static void testGetDisplayNamesWithInvalidParams() {
        Calendar cal = Calendar.getInstance();
        Map<String, Integer> result = cal.getDisplayNames(-1, -1, Locale.US);
        if (result != null) {
            throw new AssertionError("Expected null for invalid parameters");
        }
    }

    static void testGetDisplayNamesWithFallback() {
        Calendar cal = Calendar.getInstance();
        Map<String, Integer> result = cal.getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRANCE);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty map for fallback case");
        }
        boolean containsMonday = false;
        for (String key : result.keySet()) {
            if (key.equals("lun.") || key.equals("Lun")) {
                containsMonday = true;
                break;
            }
        }
        if (!containsMonday) {
            throw new AssertionError("Map should contain "lun." or "Lun" for Monday in French");
        }
    }
}
